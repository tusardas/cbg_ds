package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Round;
import com.heytusar.cbg.core.models.Turn;
import com.heytusar.cbg.core.models.User;

@Repository
@Transactional
public class RoundRepositoryImpl implements RoundRepositoryCustom {
private static final Logger log = LoggerFactory.getLogger(RoundRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Round getCurrentRound(Game game) {
		Round round = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT round FROM Round round ");
		query.append(" WHERE round.gameId = :gameId ");
		query.append(" AND round.isCurrent IS TRUE ");
		List<Round> rounds = entityManager.createQuery(query.toString(), Round.class)
				.setParameter("gameId", game.getId())
				.setMaxResults(1)
				.getResultList();
		log.info("rounds ----> " + rounds);
		if(rounds.size() > 0) {
			round = rounds.get(0);
		}
		return round;
	}
	
	public Round saveNewRound(Round round) {
		entityManager.merge(round);
		entityManager.persist(round);
		Long roundId = round.getId();
		for(Turn turn : round.getTurns()) {
			turn.setRoundId(roundId);
			entityManager.merge(turn);
		}
		return round;
	}
}
