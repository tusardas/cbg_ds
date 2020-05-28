package com.heytusar.cbg.api.persistence;

import com.heytusar.cbg.core.models.Role;
import com.heytusar.cbg.core.models.User;

public interface UserRepositoryCustom {

	User findByRole(Role role);
}
