package com.tutorial.tutorial.service;

import java.util.Optional;

import com.tutorial.tutorial.model.User;

public interface UserService {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User save(User user);
}
