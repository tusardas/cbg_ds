package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Game;
import com.heytusar.cbg.core.models.Turn;


@Repository
@Transactional
public interface TurnRepository extends CrudRepository<Turn, Long>, TurnRepositoryCustom {
	
}
