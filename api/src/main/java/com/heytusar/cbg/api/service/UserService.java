package com.heytusar.cbg.api.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytusar.cbg.api.persistence.UserRepository;
import com.heytusar.cbg.core.models.User;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		Iterable<User> iusers = userRepository.findAll();
		
		log.info("iusers -------->" + iusers);
		
		//iusers.forEach(userList::add);
		Iterator<User> it = iusers.iterator();
		while (it.hasNext()){
			userList.add(it.next());
		}
		log.info("userList ---> " + userList);
		return userList;
	}
}
