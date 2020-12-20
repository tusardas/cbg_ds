package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.PlayedCard;

@Repository
@Transactional
public interface PlayedCardRepository extends CrudRepository<PlayedCard, Long>{
	List<PlayedCard> findAllByTurnId(Long turnId);
	
	List<PlayedCard> findAllByGameIdAndTurnIdAndIsPlayed(Long gameId, Long turnId, Boolean isPlayed);
	
	PlayedCard findByGameIdAndTurnIdAndPlayerIdAndIsPlayed(Long gameId, Long turnId, Long playerId, Boolean isPlayed);
}
