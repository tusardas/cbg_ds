package com.heytusar.cbg.api.config;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.heytusar.cbg.api.service.SessionService;
import com.heytusar.cbg.api.service.UrlService;

@Order(Ordered.LOWEST_PRECEDENCE)
@Component
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private SessionService sessionService;
	private UrlService urlService;
	
	@Autowired
	public AuthenticationFilter(SessionService sessionService, UrlService urlService) {
		this.sessionService = sessionService;
		this.urlService = urlService;
	}
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        log.info("path ----> " + path);
        return urlService.isPublicUrl(path);
    }
	
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    	log.info("IN  AuthenticationFilter " + httpServletRequest);
        InputStream inputStream = httpServletRequest.getInputStream();
        byte[] body = StreamUtils.copyToByteArray(inputStream);
        String jsonString = new String(body);
        log.info("In AuthenticationFilter. Request body is: " + jsonString);
        
        Boolean result = false;
    	if(jsonString.length() > 0) {
	    	JSONObject json = new JSONObject(jsonString);
	    	String sessionId = (String) json.get("sessionId");
	    	log.info("sessionId ---> " + sessionId);
	    	if(sessionId.length() > 0) { 
		    	log.info("sessionService ---> " + sessionService);
		    	result = sessionService.validateSessionId(sessionId);
	    	}
    	}
    	if(!result) {
    		throw new ResponseStatusException(
			  HttpStatus.FORBIDDEN, "Authentication failed"
			);
    	}
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}