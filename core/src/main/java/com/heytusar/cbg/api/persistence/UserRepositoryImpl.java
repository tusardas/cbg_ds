package com.heytusar.cbg.api.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heytusar.cbg.core.models.Role;
import com.heytusar.cbg.core.models.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {
private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User findByRole(Role role) {
		User user = null;
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT user FROM User user ");
		query.append(" JOIN FETCH user.userRoles userRole ");
		query.append(" WHERE userRole.role.id = :roleId ");
		query.append(" ORDER BY user.id DESC ");
		List<User> users = entityManager.createQuery(query.toString(), User.class)
				.setParameter("roleId", role.getId())
				.setMaxResults(1)
				.getResultList();
		log.info("users ----> " + users);
		if(users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}
}
