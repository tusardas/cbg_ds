package com.heytusar.cbg.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
	private static final Logger log = LoggerFactory.getLogger(UrlService.class);
	
	private List<String> publicUrls = new ArrayList<String>();
	
	public UrlService() {
		this.publicUrls.add("/validateAuth");
		this.publicUrls.add("/images");
	}
	
	public boolean isPublicUrl(String path) {
		for(String publicUrl : publicUrls) {
        	if(path.startsWith(publicUrl)) {
        		return true;
        	}
        }
        return false;
	}
}
