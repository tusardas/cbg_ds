package com.heytusar.cbg.api.service;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.heytusar.cbg.api.persistence.UserSessionRepository;
import com.heytusar.cbg.core.models.UserSession;

@Service
@Transactional
public class SessionService {
	
	private static final Logger log = LoggerFactory.getLogger(SessionService.class);
	
	@Autowired
	private UserSessionRepository userSessionRepository;
	
	public UserSession getSession(Long userId) {
		UserSession userSession = userSessionRepository.findByUserId(userId);
		if(userSession == null) {
			userSession = new UserSession();
			userSession.setUserId(userId);
			final String uuid = UUID.randomUUID().toString().replace("-", "");
			userSession.setSessionId(uuid);
		}
		else {
			userSession.setLastHit(new Date());
		}
		userSessionRepository.save(userSession);
		return userSession;
	}
	
	public Boolean validateAuth(JSONObject json) {
		Boolean result = false;
		String sessionId = (String) json.get("sessionId");
    	log.info("sessionId ---> " + sessionId);
    	if(sessionId.length() > 0) { 
	    	result = validateSessionId(sessionId);
    	}
    	if(!result) {
    		throw new ResponseStatusException(
    				HttpStatus.FORBIDDEN, "Authentication failed"
			);
    	}
    	return result;
	}
	
	public Boolean validateSessionId(String sessionId) {
		log.info("validateSessionId sessionId ----> " + sessionId);
		Boolean result = false;
		UserSession userSession = userSessionRepository.findBySessionId(sessionId);
		log.info("validateSessionId userSession ----> " + userSession);
		if(userSession != null) {
			userSession.setLastHit(new Date());
			userSessionRepository.save(userSession);
			result = true;
		}
		log.info("validateSessionId result ---> " + result);
		return result;
	}
}
