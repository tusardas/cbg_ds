package com.heytusar.cbg.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytusar.cbg.api.persistence.UserRepository;
import com.heytusar.cbg.api.persistence.UserSessionRepository;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.User;
import com.heytusar.cbg.core.models.UserSession;

@Service
@Transactional
public class AuthService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private SessionService sessionService;
	
	public Map<?,?> validateAuth(Map<?,?> payload) {
		Map result = new HashMap<String, String>();
		result.put("status", "failed");
		String email = (String) payload.get("email");
		String password = (String) payload.get("password");
		
		User user = userRepository.findByEmailAndPassword(email, password);
		log.info("user -----> " + user);
		if(user != null) {
			UserSession userSession = sessionService.getSession(user.getId());
			Player player = playerService.getPlayerByUser(user);
			result.put("sessionId", userSession.getSessionId());
			result.put("playerId", player.getId());
			result.put("status", "ok");
		}
		else {
			result.put("errMsg", "Username/password combination not found");
		}
		return result;
	}
}
