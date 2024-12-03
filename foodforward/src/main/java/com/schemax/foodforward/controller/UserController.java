package com.schemax.foodforward.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.model.User;
import com.schemax.foodforward.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) {
		Optional<User> userRetrieved = userService.getUserById(userId);
		if (userRetrieved.isPresent()) {
			User user = userRetrieved.get();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
}