package com.heytusar.cbg.api.persistence;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Turn;

public interface TurnRepositoryCustom {

	Turn getCurrentTurn(Game game);
}
