package com.heytusar.cbg.api.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heytusar.cbg.api.service.GameService;
import com.heytusar.cbg.api.service.SessionService;
import com.heytusar.cbg.core.models.Game;

@Controller
public class GameController {
	
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private SessionService sessionService;
	
	@CrossOrigin
	@RequestMapping(value="/getGameByPlayer/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> getGameByPlayer(
		@PathVariable Long playerId,
		@RequestBody String jsonBody
	) throws Exception {
		JSONObject json = new JSONObject(jsonBody);
		log.info("json in controller ---> " + json);
		Boolean authStatus = sessionService.validateAuth(json);
		log.info("authStatus ----> " + authStatus);
		Game game = gameService.getGameByPlayer(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@RequestMapping(value="/saveNewGame/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> saveNewGame(
		@PathVariable Long playerId,
		@RequestBody String jsonBody
	) {
		JSONObject json = new JSONObject(jsonBody);
		log.info("json in controller ---> " + json);
		
		Boolean authStatus = sessionService.validateAuth(json);
		log.info("authStatus ----> " + authStatus);
		
		Game game = gameService.saveNewGameTwoPlayerOdi(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/turn/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> playTurn(
		@PathVariable Long playerId,
		@RequestBody String jsonBody
	) {
		JSONObject json = new JSONObject(jsonBody);
		log.info("json in controller ---> " + json);
		
		Boolean authStatus = sessionService.validateAuth(json);
		log.info("authStatus ----> " + authStatus);
		
		Game game = gameService.playTurn(playerId, json);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
}
