package com.heytusar.cbg.api.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.GameSettings;
import com.heytusar.cbg.core.models.GameState;

@Repository
@Transactional
public class GameRepositoryImpl implements GameRepositoryCustom {
	
	private static final Logger log = LoggerFactory.getLogger(GameRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public Game saveNewGame(Game game) {
		
		List<GamePlayer> gamePlayers = game.getGamePlayers();
		GameSettings gameSettings = game.getGameSettings();
		GameState gameState = game.getGameState();
		
		entityManager.persist(gameSettings);
		entityManager.persist(gameState);
		entityManager.persist(game);
		
		gameSettings.setGameId(game.getId());
		entityManager.merge(gameSettings);
		gameState.setGameId(game.getId());
		entityManager.merge(gameState);
		
		for(GamePlayer gamePlayer2 : gamePlayers) {
			gamePlayer2.setGameId(game.getId());
			entityManager.merge(gamePlayer2);
		}
		return game;
	}
	
	public Game getIncompleteGameByPlayer(Long playerId) throws Exception {
		Game game = null;
		Long gameId = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT game.id AS gameId FROM game game ");
		query.append(" INNER JOIN game_player gp ON gp.game_id = game.id ");
		query.append(" WHERE gp.player_id = ? ");
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
