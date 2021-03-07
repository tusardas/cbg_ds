package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Turn;

@Repository
@Transactional
public interface TurnRepository extends CrudRepository<Turn, Long>{
	List<Turn> findAllByRoundId(Long roundId);
	
	List<Turn> findAllByRoundIdAndIsPlayed(Long roundId, Boolean isPlayed);
	
	Turn findByRoundIdAndPlayerIdAndIsPlayed(Long roundId, Long playerId, Boolean isPlayed);
}
