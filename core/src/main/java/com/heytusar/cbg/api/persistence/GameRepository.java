package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;

@Repository
@Transactional
public interface GameRepository extends CrudRepository<Game, Long>, GameRepositoryCustom {
	
}