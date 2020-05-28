package com.heytusar.cbg.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytusar.cbg.api.persistence.RoleRepository;
import com.heytusar.cbg.core.models.Role;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	Role getRoleByName(String roleName) {
		return roleRepository.findByName(roleName);
	}
}
