package com.heytusar.cbg.api.data;

import com.heytusar.cbg.core.models.Player;

public class PlayerData {
	
	private Long playerId;
	private String playerName;
	
	public PlayerData() {
		
	}
	
	public PlayerData(Player player) {
		this.playerId = player.getId();
		this.playerName = player.getDisplayName();
	}
}
