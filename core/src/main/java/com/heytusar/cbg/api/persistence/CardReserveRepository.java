package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.CardReserve;

@Repository
@Transactional
public interface CardReserveRepository extends CrudRepository<CardReserve, Long>, CardReserveRepositoryCustom{
	
	List<CardReserve> findAllByGameIdAndPlayerIdAndReserveType(Long gameId, Long playerId, Integer reserveType);
}
