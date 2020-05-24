package com.heytusar.cbg.api.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
