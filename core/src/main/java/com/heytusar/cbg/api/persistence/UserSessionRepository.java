package com.heytusar.cbg.api.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.UserSession;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {
	
	UserSession findByUserId(Long userId);
	
	UserSession findBySessionId(String sessionId);
}
