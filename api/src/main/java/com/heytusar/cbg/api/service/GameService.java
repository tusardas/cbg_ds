package com.heytusar.cbg.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.heytusar.cbg.api.persistence.CardRepository;
import com.heytusar.cbg.api.persistence.CardReserveRepository;
import com.heytusar.cbg.api.persistence.GameRepository;
import com.heytusar.cbg.api.persistence.PlayedCardRepository;
import com.heytusar.cbg.api.persistence.PlayerRepository;
import com.heytusar.cbg.api.persistence.TurnRepository;
import com.heytusar.cbg.core.models.Card;
import com.heytusar.cbg.core.models.CardAttribute;
import com.heytusar.cbg.core.models.CardReserve;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.GameSettings;
import com.heytusar.cbg.core.models.GameState;
import com.heytusar.cbg.core.models.PlayedCard;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Role;
import com.heytusar.cbg.core.models.Turn;
import com.heytusar.cbg.core.models.User;
import com.heytusar.cbg.core.models.UserRole;
import com.heytusar.cbg.core.utils.DifficultyLevelEnum;
import com.heytusar.cbg.core.utils.GameFormatEnum;
import com.heytusar.cbg.core.utils.GamePlayerStatusEnum;
import com.heytusar.cbg.core.utils.GameStatusEnum;
import com.heytusar.cbg.core.utils.ReserveTypeEnum;
import com.heytusar.cbg.core.utils.RoleEnum;

@Service
@Transactional
public class GameService {
	private static final Logger log = LoggerFactory.getLogger(GameService.class);
	private ApplicationContext appContext;
	
	private GameRepository gameRepository;
	private CardReserveRepository cardReserveRepository;
	private PlayedCardRepository playedCardRepository;
	private TurnRepository turnRepository;
	private CardRepository cardRepository;
	private PlayerRepository playerRepository;
	
    @Autowired
	public GameService(ApplicationContext appContext, 
			GameRepository gameRepository,
			CardReserveRepository cardReserveRepository,
			PlayedCardRepository playedCardRepository,
			TurnRepository turnRepository,
			CardRepository cardRepository,
			PlayerRepository playerRepository
			) {
		this.appContext = appContext;
		this.gameRepository = gameRepository;
		this.cardReserveRepository = cardReserveRepository;
		this.playedCardRepository = playedCardRepository;
		this.turnRepository = turnRepository;
		this.cardRepository = cardRepository;
		this.playerRepository = playerRepository;
	}
	
	public Map<String, Object> getGameByPlayer(Long playerId) throws Exception {
		Player player = appContext.getBean(PlayerService.class).getPlayerById(playerId);
		Game game = gameRepository.getIncompleteGameByPlayer(player.getId());
		
		if(game == null) {
			throw new ResponseStatusException(
			  HttpStatus.NO_CONTENT, "No game found"
			);
		}
		
		Turn turn = turnRepository.getCurrentTurn(game);
		
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("game", game);
		models.put("turn", turn);
		return models;
	}

	public Map<String, Object> saveNewGameTwoPlayerOdi(Long playerId) {
		PlayerService playerService = appContext.getBean(PlayerService.class);
		Player player = playerService.getPlayerById(playerId);
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
		gamePlayer.setGamePlayerStatus(GamePlayerStatusEnum.WAITING.getValue());
		
		GamePlayer adminGamePlayer = new GamePlayer();
		adminGamePlayer.setPlayer(adminPlayer);
		adminGamePlayer.setSerialNum(2);
		adminGamePlayer.setGamePlayerStatus(GamePlayerStatusEnum.WAITING.getValue());
		
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
		
		Turn turn = new Turn();
		turn.setTurnNo(1L);
		turn.setIsCurrentTurn(true);
		turn.setIsTurnSettled(false);
		turn.setNextPlayerId(playerId);
		
		Game game = new Game();
		game.setGameSettings(gameSettings);
		game.setGameState(gameState);
		game.setGamePlayers(gamePlayers);
		
		Map<String, Object> saveGameResult = gameRepository.saveNewGame(game, turn);
		
		Game savedGame = (Game) saveGameResult.get("game");
		
		for(GamePlayer gp : gamePlayers) {
			PlayedCard playedCard = new PlayedCard();
			playedCard.setGameId(savedGame.getId());
			playedCard.setTurnId(turn.getId());
			playedCard.setPlayerId(gp.getPlayer().getId());
			playedCard.setIsPlayed(false);
			playedCardRepository.save(playedCard);
		}
		
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("game", saveGameResult.get("game"));
		models.put("turn", saveGameResult.get("result"));
		return models;
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

	public Map<String, Object> playTurn(Long playerId, JSONObject json) throws Exception {
		Player player = appContext.getBean(PlayerService.class).getPlayerById(playerId);
		log.info("player ----> " + player);
		log.info("json ----> " + json);
		JSONObject cardReserveJson = json.getJSONObject("cardReserve");
		JSONObject cardAttributeJson = json.getJSONObject("cardAttribute");
		log.info("cardReserveJson ---> " +cardReserveJson.toString());
		log.info("cardAttributeJson ---> " +cardAttributeJson.toString());
		
		String attributeKey = cardAttributeJson.getString("attributeKey");
		Long gameId = cardReserveJson.getLong("gameId");
		Game game = gameRepository.findById(gameId).orElse(null);
		log.info("game ----> " + game);
		
		Turn turn = turnRepository.getCurrentTurn(game);
		GameState gameState = game.getGameState();
		GameSettings gameSettings = game.getGameSettings();
		if(turn.getNextPlayerId() == playerId && gameState.getGameStatus() != 3) {
			Long cardReserveId = cardReserveJson.getLong("id");
			CardReserve	cardReserve	= cardReserveRepository.findById(cardReserveId).orElse(null);
			Long cardId = cardReserve.getCard().getId();
			log.info("cardReserve ------> " + cardReserve);
					
			GamePlayer gamePlayer = gameRepository.getGamePlayerByGameAndPlayer(game, player);
			gamePlayer.removeCardReserve(cardReserve);
			gamePlayer.setGamePlayerStatus(GamePlayerStatusEnum.WAITING.getValue());
			turn.setPlayedByPlayerId(playerId);
			
			PlayedCard playedCard = playedCardRepository.findByGameIdAndTurnIdAndPlayerIdAndIsPlayed(gameId, turn.getId(), playerId, false);
			playedCard.setCardId(cardId);
			playedCard.setAttributeKeyPlayed(attributeKey);
			playedCard.setIsPlayed(true);
			playedCardRepository.save(playedCard);
			
			List<PlayedCard> playedCards = playedCardRepository.findAllByGameIdAndTurnIdAndIsPlayed(gameId, turn.getId(), true);
			
			if(playedCards.size() == gameSettings.getNumberOfPlayers()) {
				//Decision for this Turn
				List<Map> winnerList = new ArrayList<Map>();
				Map compareMap = null;
				for(PlayedCard pc : playedCards) {
					
					Card card = cardRepository.findById(pc.getCardId()).orElse(null);
					Player playerObj = playerRepository.findById(pc.getPlayerId()).orElse(null);
					CardAttribute ca = card.getCardAttribute(pc.getAttributeKeyPlayed());
					Double value = Double.parseDouble(ca.getAttributeValue());
					log.info("value -----------------------------> " + value);
					
					compareMap = new HashMap<Object, Object>();
					compareMap.put("player", playerObj);
					compareMap.put("card", card);
					compareMap.put("value", value);
					log.info("compareMap -----------------------------> " +compareMap);
					
					winnerList.add(compareMap);
				}
				int size = winnerList.size();
				int i = 0, j = 0;
				for(i = 0; i < size-1; i++) {
					for(j = 1; j < size; j++) {
						Map compareMapI = winnerList.get(i);
						Double valueI = (Double) compareMapI.get("value");
						
						Map compareMapJ = winnerList.get(j);
						Double valueJ = (Double) compareMapJ.get("value");
						
						log.info("valueI ----------------------------> " + valueI);
						log.info("valueJ ----------------------------> " + valueJ);
						
						if(valueI > valueJ) {
							winnerList.set(i, compareMapJ);
							winnerList.set(j, compareMapI);
						}
					}
				}
				
				Map winnerMap = winnerList.get(size-1);
				Player winnerPlayer = (Player) winnerMap.get("player");
				Card winnerCard = (Card) winnerMap.get("card");
				
				log.info("winnerMap -------> " + winnerMap);
				log.info("winnerPlayer -------> " + winnerPlayer);
				log.info("winnerCard -------> " + winnerCard);
				
				GamePlayer winnerGamePlayer = gameRepository.getGamePlayerByGameAndPlayer(game, winnerPlayer);
				List <PlayedCard> allPlayedCardsInTurn = playedCards;
				for(PlayedCard playedCardInTurn : allPlayedCardsInTurn) {
					CardReserve wonCardReserve;
					Card cardWon = cardRepository.findById(playedCardInTurn.getCardId()).orElse(null);
					if(cardWon.getId() != cardReserve.getCard().getId()) {
						wonCardReserve = new CardReserve();
						wonCardReserve.setCard(cardWon);
						wonCardReserve.setGameId(winnerGamePlayer.getGameId());
						
					}
					else {
						wonCardReserve = cardReserve;
					}
					wonCardReserve.setPlayerId(winnerPlayer.getId());
					wonCardReserve.setReserveType(ReserveTypeEnum.WINNING_RESERVE.getValue());
					winnerGamePlayer.addCardReserve(wonCardReserve);
				}
				
				gameState.setNextPlayerId(winnerPlayer.getId());
				gameState.setServerPlayerId(winnerPlayer.getId());
				
				turn.setPlayedByPlayerId(playerId);
				turn.setNextPlayerId(null);
				turn.setIsTurnSettled(true);
				turn.setIsCurrentTurn(false);
				Turn newTurn = null;
				
				Map<String, Object> models = new HashMap<String, Object>();
				models.put("game", game);
				models.put("turn", turn);
				
				Integer brokePlayersCount = determineBroke(game);
				if((brokePlayersCount + 1) == gameSettings.getNumberOfPlayers()) {
					gameState.setGameStatus(GameStatusEnum.FINISHED.getValue());
					log.info("game has ended ----------------------------->");
				}
				else {
					newTurn = initNewTurn(game, winnerGamePlayer, turn);
					turnRepository.save(newTurn);
					models.put("turn", newTurn);
					log.info("new turn initiated ----------------------------->");
				}
				winnerGamePlayer.setGamePlayerStatus(GamePlayerStatusEnum.THINKING.getValue());
				return models;
			}
			else {
				//Other player's turn is pending
				GamePlayer nextGamePlayer = null;
				List<GamePlayer> gamePlayers = game.getGamePlayers();
				for(GamePlayer gp : gamePlayers) {
					if(gp.getSerialNum() == gamePlayer.getSerialNum() + 1) {
						nextGamePlayer = gp;
						break;
					}
				}
				
				Long nextPlayerId = nextGamePlayer.getPlayer().getId();
				nextGamePlayer.setGamePlayerStatus(GamePlayerStatusEnum.THINKING.getValue());
				gameState.setNextPlayerId(nextPlayerId);
				turn.setNextPlayerId(nextPlayerId);
				turn.setPlayedByPlayerId(playerId);
			}
			turnRepository.save(turn);
			gameRepository.save(game);
		}
		
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("game", game);
		models.put("turn", turn);
		return models;
	}

	private Turn initNewTurn(Game game, GamePlayer winnerGamePlayer, Turn currentTurn) {
		Turn turn = new Turn();
		turn.setGameId(game.getId());
		turn.setTurnNo(currentTurn.getTurnNo()+1);
		turn.setIsCurrentTurn(true);
		turn.setIsTurnSettled(false);
		turn.setNextPlayerId(winnerGamePlayer.getPlayer().getId());
		
		List<GamePlayer> gamePlayers = gameRepository.getGamePlayersByGame(game);
		for(GamePlayer gamePlayer : gamePlayers) {
			PlayedCard playedCard = new PlayedCard();
			playedCard.setGameId(game.getId());
			playedCard.setTurnId(turn.getId());
			playedCard.setPlayerId(gamePlayer.getPlayer().getId());
			playedCard.setIsPlayed(false);
			playedCardRepository.save(playedCard);
		}
		return turn;
	}

	private Integer determineBroke(Game game) {
		Integer reallyBrokePlayersCount = 0;
		List<GamePlayer> gamePlayers = gameRepository.getGamePlayersByGame(game);
		for(GamePlayer gamePlayer : gamePlayers) {
			List<CardReserve> cardReserveList = cardReserveRepository.findAllByGameIdAndPlayerIdAndReserveType(gamePlayer.getGameId(), gamePlayer.getPlayer().getId(), 1);
			if(cardReserveList.size() == 0) {
				log.info("gamePlayer is broke -----------------> " + gamePlayer.getId());
				List<CardReserve> wonCardReserveList = cardReserveRepository.findAllByGameIdAndPlayerIdAndReserveType(gamePlayer.getGameId(), gamePlayer.getPlayer().getId(), 2);
				for(CardReserve wonCardReserve : wonCardReserveList) {
					wonCardReserve.setReserveType(1);
					cardReserveRepository.save(wonCardReserve);
				}
				if(wonCardReserveList.size() == 0) {
					reallyBrokePlayersCount++;
					log.info("gamePlayer is really broke -----------------> " + gamePlayer.getId());
				}
			}
		}
		return reallyBrokePlayersCount;
	}
}
