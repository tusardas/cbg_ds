package com.heytusar.cbg.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heytusar.cbg.api.service.UserService;
import com.heytusar.cbg.core.models.User;

@RestController
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> userList = userService.getUsers();
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
}
