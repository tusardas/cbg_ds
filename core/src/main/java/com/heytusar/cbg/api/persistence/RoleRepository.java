package com.heytusar.cbg.api.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Role;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
}
