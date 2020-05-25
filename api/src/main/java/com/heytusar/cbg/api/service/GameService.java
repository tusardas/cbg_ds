package com.heytusar.cbg.api.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.heytusar.cbg.api.persistence.GameRepository;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Player;

@Service
@Transactional
public class GameService {
	private static final Logger log = LoggerFactory.getLogger(GameService.class);
	private ApplicationContext appContext;
	private GameRepository gameRepository;
	
    @Autowired
	public GameService(ApplicationContext appContext, GameRepository gameRepository) {
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

	public Game saveNewGame(Long playerId) {
		Player player = appContext.getBean(PlayerService.class).getPlayerById(playerId);
		Game game = gameRepository.saveNewGame(player.getId());
		return game;
	}

}
