package com.heytusar.cbg.api.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heytusar.cbg.api.service.AuthService;

@RestController
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;
	
	@CrossOrigin
	@RequestMapping(value="/validateAuth", method = RequestMethod.POST)
	public ResponseEntity<Map<?,?>> login(@RequestBody Map<?, ?> payload) {
		Map<?,?> result = authService.validateAuth(payload);
		log.info("result ----> " + result);
		return new ResponseEntity<Map<?,?>>(result, HttpStatus.OK);
	}
}
