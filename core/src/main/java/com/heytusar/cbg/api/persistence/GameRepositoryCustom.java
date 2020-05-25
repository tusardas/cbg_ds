package com.heytusar.cbg.api.persistence;

import com.heytusar.cbg.core.models.Game;

public interface GameRepositoryCustom {
	Game getIncompleteGameByPlayer(Long playerId) throws Exception;
	Game saveNewGame(Long playerId);
}
