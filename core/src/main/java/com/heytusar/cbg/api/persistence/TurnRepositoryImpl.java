package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Turn;
import com.heytusar.cbg.core.models.User;

@Repository
@Transactional
public class TurnRepositoryImpl implements TurnRepositoryCustom {
private static final Logger log = LoggerFactory.getLogger(TurnRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Turn getCurrentTurn(Game game) {
		Turn turn = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT turn FROM Turn turn ");
		query.append(" WHERE turn.gameId = :gameId ");
		query.append(" AND turn.isCurrentTurn IS TRUE ");
		List<Turn> turns = entityManager.createQuery(query.toString(), Turn.class)
				.setParameter("gameId", game.getId())
				.setMaxResults(1)
				.getResultList();
		log.info("turns ----> " + turns);
		if(turns.size() > 0) {
			turn = turns.get(0);
		}
		return turn;
	}
}
