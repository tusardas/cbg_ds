package com.heytusar.cbg.core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameState {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer gameStatus; // 1 = in progress, 2 = paused, 3 = finished, see GameStatusEnum.java
    
	private Long gameId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(Integer gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
}
