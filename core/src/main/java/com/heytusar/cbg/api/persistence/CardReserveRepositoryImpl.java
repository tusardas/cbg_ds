package com.heytusar.cbg.api.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.CardReserve;
import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.GameSettings;
import com.heytusar.cbg.core.models.GameState;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.Round;
import com.heytusar.cbg.core.models.Turn;

@Repository
@Transactional
public class CardReserveRepositoryImpl implements CardReserveRepositoryCustom {
	
	private static final Logger log = LoggerFactory.getLogger(CardReserveRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public CardReserve findByCardIdAndGameId(Long cardId, Long gameId) {
		CardReserve cardReserve = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT cr FROM CardReserve cr ");
		query.append(" WHERE cr.card.id = :cardId ");
		query.append(" AND cr.gameId = :gameId ");
		List<CardReserve> cardReserves = entityManager.createQuery(query.toString(), CardReserve.class)
				.setParameter("cardId", cardId)
				.setParameter("gameId", gameId)
				.getResultList();
		if(cardReserves.size() > 0) {
			cardReserve = cardReserves.get(0);
		}
		return cardReserve;
	}
}
