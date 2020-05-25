package com.heytusar.cbg.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytusar.cbg.api.persistence.PlayerRepository;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.User;

@Service
@Transactional
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	
	Player getPlayerById(Long playerId) {
		return playerRepository.findById(playerId).orElse(null);
	}
	
	Player getPlayerByUser(User user) {
		Player player = playerRepository.findByUser(user);
		return player;
	}
}
