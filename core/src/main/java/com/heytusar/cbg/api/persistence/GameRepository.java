package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.GamePlayer;
import com.heytusar.cbg.core.models.Player;

@Repository
@Transactional
public interface GameRepository extends CrudRepository<Game, Long>, GameRepositoryCustom {
	
}
