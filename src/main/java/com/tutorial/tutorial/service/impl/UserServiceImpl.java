package com.tutorial.tutorial.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.tutorial.model.User;
import com.tutorial.tutorial.repository.UserRepository;
import com.tutorial.tutorial.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public Boolean existsByUsername(String username) {
		try {
			userRepository.existsByUsername(username);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean existsByEmail(String email) {
		try {
			userRepository.existsByEmail(email);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public User save(User user) {
		User savedUser = userRepository.save(user);
		return savedUser;
	}

}
