package com.tutorial.tutorial.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.tutorial.model.ERole;
import com.tutorial.tutorial.model.Role;
import com.tutorial.tutorial.repository.RoleRepository;
import com.tutorial.tutorial.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(ERole user) {
		Optional<Role> userRole = roleRepository.findByName(user);
		return userRole;
	}

}
