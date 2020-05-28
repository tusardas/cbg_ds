package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
	User findByEmailAndPassword(String email, String password);
}
