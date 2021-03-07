package com.heytusar.cbg.core.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Round {
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
	private Long roundNum;
	
	@Column(columnDefinition = "TINYINT", length = 1)
	private Boolean isCurrent;
	
	@Column(columnDefinition = "TINYINT", length = 1)
	private Boolean isSettled;
	
	@Column(nullable = true)
	private Long serverPlayerId;
	
	@Column(nullable = true)
	private Long nextPlayerId;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)  
	private List<Turn> turns;

	public List<Turn> getTurns() {
		return turns;
	}
	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}
	

	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public Long getRoundNum() {
		return roundNum;
	}
	public void setRoundNum(Long roundNum) {
		this.roundNum = roundNum;
	}
	public Boolean getIsCurrentTurn() {
		return isCurrent;
	}
	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public Boolean getIsSettled() {
		return isSettled;
	}
	public void setIsSettled(Boolean isSettled) {
		this.isSettled = isSettled;
	}
	public Long getNextPlayerId() {
		return nextPlayerId;
	}
	public void setNextPlayerId(Long nextPlayerId) {
		this.nextPlayerId = nextPlayerId;
	}
	public Long getServerPlayerId() {
		return serverPlayerId;
	}
	public void setServerPlayerId(Long serverPlayerId) {
		this.serverPlayerId = serverPlayerId;
	}
}
