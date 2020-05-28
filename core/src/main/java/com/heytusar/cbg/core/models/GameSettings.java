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
	
	private Integer numberOfPlayers;
    private String gameFormat;//'test', 'odi', 't20', see GameFormatEnum.java
    private Integer difficultyLevel; // 1 = easy, 2 = medium, 3 = hard, see DifficultyLevelEnum.java
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
	public String getGameFormat() {
		return gameFormat;
	}
	public void setGameFormat(String gameFormat) {
		this.gameFormat = gameFormat;
	}
	public Integer getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(Integer difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
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
