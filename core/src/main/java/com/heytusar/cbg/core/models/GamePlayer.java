package com.heytusar.cbg.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GamePlayer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long gameId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="player_id_FK", nullable = false)
	private Player player;
	
	private Integer serialNum; //based on this number player will show his card
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<CardReserve> cardReserves;
	
	private Integer gamePlayerStatus; //1 = thinking, 2 = waiting, See GamePlayerStatusEnum.java
	
	public List<CardReserve> getCardReserves() {
		return cardReserves;
	}

	public void setCardReserves(List<CardReserve> cardReserves) {
		this.cardReserves = cardReserves;
	}
	
	public void addCardReserve(CardReserve cardReserve) {
		List<CardReserve> cardReserves = this.cardReserves;
		if(cardReserves == null) {
			cardReserves = new ArrayList<CardReserve>();
		}
		cardReserves.add(cardReserve);
		setCardReserves(cardReserves);
	}
	
	public void removeCardReserve(CardReserve cardReserve) {
		List<CardReserve> cardReserves = this.cardReserves;
		if(cardReserves == null) {
			return;
		}
		cardReserves.remove(cardReserve);
	}
	
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getGamePlayerStatus() {
		return gamePlayerStatus;
	}

	public void setGamePlayerStatus(Integer gamePlayerStatus) {
		this.gamePlayerStatus = gamePlayerStatus;
	}
}
