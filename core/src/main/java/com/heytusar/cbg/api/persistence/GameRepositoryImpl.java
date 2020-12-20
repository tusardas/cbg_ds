package com.heytusar.cbg.api.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.CardReserve;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.GameSettings;
import com.heytusar.cbg.core.models.GameState;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Turn;

@Repository
@Transactional
public class GameRepositoryImpl implements GameRepositoryCustom {
	
	private static final Logger log = LoggerFactory.getLogger(GameRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public Map<String, Object> saveNewGame(Game game, Turn turn) {
		
		List<GamePlayer> gamePlayers = game.getGamePlayers();
		GameSettings gameSettings = game.getGameSettings();
		GameState gameState = game.getGameState();
		
		entityManager.persist(gameSettings);
		entityManager.persist(gameState);
		entityManager.persist(game);
		entityManager.persist(turn);
		
		Long gameId = game.getId();
		
		gameSettings.setGameId(gameId);
		entityManager.merge(gameSettings);
		
		gameState.setGameId(gameId);
		entityManager.merge(gameState);
		
		List<CardReserve> cardReserveList;
		for(GamePlayer gamePlayer2 : gamePlayers) {
			cardReserveList = gamePlayer2.getCardReserves();
			for(CardReserve cardReserve : cardReserveList) {
				cardReserve.setGameId(gameId);
				entityManager.merge(cardReserve);
			}
			gamePlayer2.setGameId(gameId);
			entityManager.merge(gamePlayer2);
		}
		
		
		turn.setGameId(gameId);
		entityManager.merge(turn);
		
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("game", game);
		models.put("turn", turn);
		
		return models;
	}
	
	public Game getIncompleteGameByPlayer(Long playerId) throws Exception {
		Game game = null;
		Long gameId = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT game.id AS gameId FROM game game ");
		query.append(" INNER JOIN game_player gp ON gp.game_id = game.id ");
		query.append(" WHERE gp.player_id_FK = ? ");
		PreparedStatement prepStmt = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement(query.toString());
			prepStmt.setLong(1, playerId);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next()){
				gameId = rs.getLong("gameId");
			}
			log.info("gameId ---> " + gameId);
			if(gameId != null) {
				List<Game>games = entityManager.createQuery("SELECT game FROM Game game WHERE game.id = :gameId", Game.class)
						.setParameter("gameId", gameId)
						.getResultList();
				if(games.size() > 0) {
					game = games.get(0);
				}
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			connection.close();
		}
		return game;
	}

	@Override
	public GamePlayer getGamePlayerByGameAndPlayer(Game game, Player player) throws Exception {
		GamePlayer gamePlayer = null;
		Long gamePlayerId = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT gp.id AS gamePlayerId FROM game_player gp ");
		query.append(" WHERE gp.player_id_FK = ? ");
		query.append(" AND gp.game_id = ? ");
		PreparedStatement prepStmt = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement(query.toString());
			prepStmt.setLong(1, player.getId());
			prepStmt.setLong(2, game.getId());
			
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next()){
				gamePlayerId = rs.getLong("gamePlayerId");
			}
			log.info("gamePlayerId ---> " + gamePlayerId);
			if(gamePlayerId != null) {
				List<GamePlayer> gamePlayers = entityManager.createQuery("SELECT gp FROM GamePlayer gp WHERE gp.id = :gameIdgamePlayerId", GamePlayer.class)
						.setParameter("gameIdgamePlayerId", gamePlayerId)
						.getResultList();
				if(gamePlayers.size() > 0) {
					gamePlayer = gamePlayers.get(0);
				}
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			connection.close();
		}
		return gamePlayer;
	}
	
	@Override
	public List<GamePlayer> getGamePlayersByGame(Game game) {
		List<GamePlayer> gamePlayers = entityManager.createQuery("SELECT gp FROM GamePlayer gp WHERE gp.gameId = :gameId", GamePlayer.class)
				.setParameter("gameId", game.getId())
				.getResultList();
		return gamePlayers;
	}

	/*
	public Game getIncompleteGameByPlayer(Long playerId) {
		Game game = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT game FROM Game game ");
		query.append(" JOIN FETCH game.gamePlayers gamePlayer ");
		query.append(" WHERE gamePlayer.playerId = :playerId ");
		query.append(" ORDER BY game.id DESC ");
		List<Game> games = entityManager.createQuery(query.toString(), Game.class)
				.setParameter("playerId", playerId)
				.getResultList();
		if(games.size() > 0) {
			game = games.get(0);
		}
		return game;
	}
	*/
}
