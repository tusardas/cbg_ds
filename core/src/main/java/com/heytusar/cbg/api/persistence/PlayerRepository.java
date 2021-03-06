package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.User;

@Repository
@Transactional
public interface PlayerRepository extends CrudRepository<Player, Long> {
	Player findByUser(User user);
}
