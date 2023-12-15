package com.tutorial.tutorial.service;

import java.util.Optional;

import com.tutorial.tutorial.model.ERole;
import com.tutorial.tutorial.model.Role;

public interface RoleService {
	Optional<Role> findByName(ERole user);
}
