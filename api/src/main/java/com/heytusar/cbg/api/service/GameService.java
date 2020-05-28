package com.heytusar.cbg.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.heytusar.cbg.api.persistence.GameRepository;
import com.heytusar.cbg.core.models.Card;
import com.heytusar.cbg.core.models.CardReserve;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.GameSettings;
import com.heytusar.cbg.core.models.GameState;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Role;
import com.heytusar.cbg.core.models.User;
import com.heytusar.cbg.core.models.UserRole;
import com.heytusar.cbg.core.utils.DifficultyLevelEnum;
import com.heytusar.cbg.core.utils.GameFormatEnum;
import com.heytusar.cbg.core.utils.GameStatusEnum;
import com.heytusar.cbg.core.utils.ReserveTypeEnum;
import com.heytusar.cbg.core.utils.RoleEnum;

@Service
@Transactional
public class GameService {
	private static final Logger log = LoggerFactory.getLogger(GameService.class);
	private ApplicationContext appContext;
	
	private GameRepository gameRepository;
	
    @Autowired
	public GameService(ApplicationContext appContext, 
			GameRepository gameRepository) {
		this.appContext = appContext;
		this.gameRepository = gameRepository;
	}
	
	public Game getGame(Long playerId) throws Exception {
		Player player = appContext.getBean(PlayerService.class).getPlayerById(playerId);
		Game game = gameRepository.getIncompleteGameByPlayer(player.getId());
		if(game == null) {
			throw new ResponseStatusException(
			  HttpStatus.NO_CONTENT, "No game found"
			);
		}
		return game;
	}

	public Game saveNewGameTwoPlayerOdi(Long playerId) {
		PlayerService playerService = appContext.getBean(PlayerService.class);
		Player player = appContext.getBean(PlayerService.class).getPlayerById(playerId);
		Role adminRole = appContext.getBean(RoleService.class).getRoleByName(RoleEnum.ADMIN.toString());
		if(player
			.getUser()
			.getUserRoles()
			.stream()
			.map(UserRole::getRole)
			.collect(Collectors.toList()).contains(adminRole)) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "You are not authorized to create new game"
			);
		}
		
		User adminUser = appContext.getBean(UserService.class).getUserByRole(adminRole);
		log.info("adminUser ---> " + adminUser);
		Player adminPlayer = playerService.getPlayerByUser(adminUser);
		log.info("adminPlayer ---> " + adminPlayer);
		
		List<Card> cardList = appContext.getBean(CardService.class).getAllCards();
		
		GamePlayer gamePlayer = new GamePlayer();
		gamePlayer.setPlayer(player);
		gamePlayer.setSerialNum(1);
		
		GamePlayer adminGamePlayer = new GamePlayer();
		adminGamePlayer.setPlayer(adminPlayer);
		adminGamePlayer.setSerialNum(2);
		
		List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();
		gamePlayers.add(gamePlayer);
		gamePlayers.add(adminGamePlayer);
		
		dealCards(gamePlayers, cardList);
		log.info("gamePlayer cards ---> " + gamePlayer.getCardReserves());
		log.info("adminGamePlayer cards ---> " + adminGamePlayer.getCardReserves());
		
		GameSettings gameSettings = new GameSettings();
		gameSettings.setNumberOfPlayers(gamePlayers.size());
		gameSettings.setGameFormat(GameFormatEnum.ODI.toString());
		gameSettings.setDifficultyLevel(DifficultyLevelEnum.EASY.getValue());
		gameSettings.setCardsPerPlayer(gamePlayer.getCardReserves().size());
		
		GameState gameState = new GameState();
		gameState.setGameStatus(GameStatusEnum.IN_PROGRESS.getValue());
		gameState.setNextPlayerId(playerId);
		gameState.setServerPlayerId(playerId);
		
		Game game = new Game();
		game.setGameSettings(gameSettings);
		game.setGameState(gameState);
		game.setGamePlayers(gamePlayers);
		
		game = gameRepository.saveNewGame(game);
		return game;
	}
	
	public List<GamePlayer> dealCards(List<GamePlayer> gamePlayers, List<Card> deckOfCards) {
		randomizeCards(deckOfCards);
		int numOfCards = deckOfCards.size();
		int numOfPlayers = gamePlayers.size();
		GamePlayer gamePlayer = null;
		Card card = null;
		CardReserve cardReserve = null;
		for (int i=0; i<numOfCards; i++) {
			gamePlayer = gamePlayers.get(i % numOfPlayers);
			card = deckOfCards.get(i);
			cardReserve = new CardReserve();
			cardReserve.setCard(card);
			cardReserve.setGameId(gamePlayer.getGameId());
			cardReserve.setPlayerId(gamePlayer.getPlayer().getId());
			cardReserve.setReserveType(ReserveTypeEnum.USEABLE.getValue());
			gamePlayer.addCardReserve(cardReserve);
			
			log.info("cardReserve ----> " + cardReserve);
			log.info("gamePlayer ----> " + gamePlayer);
			log.info("gamePlayer allcards ----> " + gamePlayer.getCardReserves());
        }
		return gamePlayers;
	}
	
	public void randomizeCards(List<Card> cardList) {
		Collections.shuffle(cardList, new Random()); 
	}
}
