package com.heytusar.cbg.core.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CardReserve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long gameId;
	private Long playerId;
	private Integer reserveType; //1=Useable, 2=Winning Reserve, see ReserveType.java
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="card_id_FK", nullable = false)
	private Card card;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public Integer getReserveType() {
		return reserveType;
	}
	public void setReserveType(Integer reserveType) {
		this.reserveType = reserveType;
	}
}
