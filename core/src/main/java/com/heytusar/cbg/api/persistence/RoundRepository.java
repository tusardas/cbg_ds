package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Round;


@Repository
@Transactional
public interface RoundRepository extends CrudRepository<Round, Long>, RoundRepositoryCustom {
	
}
