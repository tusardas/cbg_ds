package com.heytusar.cbg.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Turn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	private Long gameId;
	private Long turnNo;
	
	@Column(columnDefinition = "TINYINT", length = 1)
	private Boolean isCurrentTurn;
	
	@Column(columnDefinition = "TINYINT", length = 1)
	private Boolean isTurnSettled;
	
	@Column(nullable = true)
	private Long playedByPlayerId;
	
	@Column(nullable = true)
	private Long nextPlayerId;
	
	public Boolean getIsTurnSettled() {
		return isTurnSettled;
	}
	public void setIsTurnSettled(Boolean isTurnSettled) {
		this.isTurnSettled = isTurnSettled;
	}

	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public Long getTurnNo() {
		return turnNo;
	}
	public void setTurnNo(Long turnNo) {
		this.turnNo = turnNo;
	}
	public Boolean getIsCurrentTurn() {
		return isCurrentTurn;
	}
	public void setIsCurrentTurn(Boolean isCurrentTurn) {
		this.isCurrentTurn = isCurrentTurn;
	}
	public Long getPlayedByPlayerId() {
		return playedByPlayerId;
	}
	public void setPlayedByPlayerId(Long playedByPlayerId) {
		this.playedByPlayerId = playedByPlayerId;
	}
	public Long getNextPlayerId() {
		return nextPlayerId;
	}
	public void setNextPlayerId(Long nextPlayerId) {
		this.nextPlayerId = nextPlayerId;
	}
}
