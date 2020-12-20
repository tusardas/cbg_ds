package com.heytusar.cbg.api.persistence;

import java.util.List;
import java.util.Map;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Turn;

public interface GameRepositoryCustom {
	Game getIncompleteGameByPlayer(Long playerId) throws Exception;
	Map<String, Object> saveNewGame(Game game, Turn turn);
	GamePlayer getGamePlayerByGameAndPlayer(Game game, Player player) throws Exception;
	List<GamePlayer> getGamePlayersByGame(Game game);
}
