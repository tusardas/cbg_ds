package com.heytusar.cbg.api.persistence;

import org.springframework.data.repository.CrudRepository;

import com.heytusar.cbg.core.models.Game;

public interface GameRepository extends CrudRepository<Game, Long>, GameRepositoryCustom {
	
}
