package com.heytusar.cbg.core.models;

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

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "game_settings_id_FK", nullable = false)
	private GameSettings gameSettings;
	
	@OneToOne
	@JoinColumn(name = "game_state_id_FK", nullable = false)
	private GameState gameState;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<GamePlayer> gamePlayers;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public GameSettings getGameSettings() {
		return gameSettings;
	}

	public void setGameSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public List<GamePlayer> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}
	
}
