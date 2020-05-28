package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Card;

@Repository
@Transactional
public interface CardRepository extends CrudRepository<Card, Long>{
	
}
