package com.heytusar.cbg.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heytusar.cbg.api.service.GameService;
import com.heytusar.cbg.core.models.Game;

@Controller
public class GameController {
	
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	@CrossOrigin
	@RequestMapping(value="/getGameByPlayer/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<Game> getGameByPlayer(
		@PathVariable Long playerId 
	) throws Exception {
		Game game = gameService.getGameByPlayer(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/saveNewGame/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> saveNewGame(
		@PathVariable Long playerId
	) {
		Game game = gameService.saveNewGameTwoPlayerOdi(playerId);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/turn/{playerId}", method = RequestMethod.POST)
	public ResponseEntity<Game> playTurn(
		@PathVariable Long playerId,
		HttpServletRequest request, 
        HttpServletResponse response
	) {
		JSONObject json = (JSONObject) request.getAttribute("jsonBody");
		log.info("json in controller ---> " + json);
		Game game = gameService.playTurn(playerId, json);
		log.info("game ----> " + game);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
}
