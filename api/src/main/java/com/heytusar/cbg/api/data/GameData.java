package com.heytusar.cbg.api.data;

import com.heytusar.cbg.core.models.Game;

public class GameData {
	private Long gameId;
	
	public GameData() {
		
	}
	
	public GameData(Game game) {
		this.gameId = game.getId();
	}
	
}
