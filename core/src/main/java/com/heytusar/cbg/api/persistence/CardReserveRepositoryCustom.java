package com.heytusar.cbg.api.persistence;

import java.util.List;
import java.util.Map;

import com.heytusar.cbg.core.models.CardReserve;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Round;

public interface CardReserveRepositoryCustom {
	CardReserve findByCardIdAndGameId(Long cardId, Long gameId);
}
