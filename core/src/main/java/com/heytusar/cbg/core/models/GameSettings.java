package com.heytusar.cbg.core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameSettings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer numberOfPlayers; // 1 = single-player, 2 = multiplayer
    private String cricketFormat;//'test', 'odi', 't20'
    private Integer gameMode; // 1 = easy, 2 = medium, 3 = hard
    private Integer cardsPerPlayer;
    private Long gameId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public String getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(String cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public Integer getGameMode() {
		return gameMode;
	}
	public void setGameMode(Integer gameMode) {
		this.gameMode = gameMode;
	}
	public Integer getCardsPerPlayer() {
		return cardsPerPlayer;
	}
	public void setCardsPerPlayer(Integer cardsPerPlayer) {
		this.cardsPerPlayer = cardsPerPlayer;
	}
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
}
