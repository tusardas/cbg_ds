package com.heytusar.cbg.api.persistence;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Round;

public interface RoundRepositoryCustom {

	Round getCurrentRound(Game game);
	Round saveNewRound(Round round);
}
