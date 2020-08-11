package com.heytusar.cbg.api.data;

import java.util.List;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Player;

public class GameConsole {
	private GameData gameData;
	private PlayerData playerData;
	private List<PlayerData> otherPlayers;
	private List<CardData> myCards;
	private List<CardData> playedCards;
	
	public GameConsole(Game game) {
		this.gameData = new GameData(game);
	}
	
	public GameConsole(Game game, Player player) {
		this.gameData = new GameData(game);
		this.playerData = new PlayerData(player);
	}
}
