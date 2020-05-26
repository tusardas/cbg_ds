package com.heytusar.cbg.api.interceptor;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.heytusar.cbg.api.service.SessionService;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	private SessionService sessionService;
	
	@Autowired
	public AuthInterceptor(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse  response,  Object handler) throws IOException, ResponseStatusException {
    	
    	String jsonString = request.getReader().lines().collect(Collectors.joining());
    	log.info("jsonString ---> " + jsonString);
    	Boolean result = false;
    	if(jsonString.length() > 0) {
	    	JSONObject json = new JSONObject(jsonString);
	    	String sessionId = (String) json.get("sessionId");
	    	log.info("sessionId ---> " + sessionId);
	    	if(sessionId.length() > 0) { 
		    	log.info("sessionService ---> " + sessionService);
		    	result = sessionService.validateSessionId(sessionId);
	    	}
	    	request.setAttribute("jsonBody", json);
    	}
    	if(!result) {
    		throw new ResponseStatusException(
			  HttpStatus.FORBIDDEN, "Authentication failed"
			);
    	}
    	return result;
    }
}
