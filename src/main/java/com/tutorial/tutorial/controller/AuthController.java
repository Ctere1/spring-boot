package com.tutorial.tutorial.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.tutorial.model.ERole;
import com.tutorial.tutorial.model.Role;
import com.tutorial.tutorial.model.User;
import com.tutorial.tutorial.payload.request.LoginRequest;
import com.tutorial.tutorial.payload.request.SignupRequest;
import com.tutorial.tutorial.payload.response.JwtResponse;
import com.tutorial.tutorial.payload.response.MessageResponse;
import com.tutorial.tutorial.security.jwt.JwtUtils;
import com.tutorial.tutorial.security.services.UserDetailsImpl;
import com.tutorial.tutorial.service.impl.RoleServiceImpl;
import com.tutorial.tutorial.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth", description = "Authentication Endpoints")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	RoleServiceImpl roleServiceImpl;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostPersist
	@PostLoad
	@PostConstruct
	private void initializeRoles() {
		// Check if roles exist, and insert them if not
		if (!roleServiceImpl.existsByName(ERole.ROLE_USER)) {
			roleServiceImpl.save(new Role(ERole.ROLE_USER));
		}

		if (!roleServiceImpl.existsByName(ERole.ROLE_MODERATOR)) {
			roleServiceImpl.save(new Role(ERole.ROLE_MODERATOR));
		}

		if (!roleServiceImpl.existsByName(ERole.ROLE_ADMIN)) {
			roleServiceImpl.save(new Role(ERole.ROLE_ADMIN));
		}
	}

	@PostMapping("/signin")
	@Operation(summary = "Authenticate user", description = "Authenticate user with username and password")
	@ApiResponses(value = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User authenticated successfully!"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error: Username is not found!"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error: Password is not correct!") })
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	@Operation(summary = "Create Authenticated user", description = "Create Authenticated user with username, email and password")
	@ApiResponses(value = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully!"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error: Username is already taken!"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error: Email is already in use!") })
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userServiceImpl.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userServiceImpl.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleServiceImpl.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleServiceImpl.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleServiceImpl.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleServiceImpl.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userServiceImpl.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
