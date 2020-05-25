package com.heytusar.cbg.api.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heytusar.cbg.api.service.GameService;
import com.heytusar.cbg.core.models.Game;

@Controller
public class GameController {
	
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="/game/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<Game> getGame(
		@PathVariable Long playerId, 
		@RequestBody Map<?, ?> payload 
	) throws Exception {
		Game game = gameService.getGame(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@RequestMapping(value="/game/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> saveNewGame(
		@PathVariable Long playerId, 
		@RequestBody Map<?, ?> payload 
	) {
		Game game = gameService.saveNewGame(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
}
