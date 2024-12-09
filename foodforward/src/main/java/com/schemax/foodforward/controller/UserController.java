package com.schemax.foodforward.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Recipient;
import com.schemax.foodforward.model.User;
import com.schemax.foodforward.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/donor")
	public ResponseEntity<Donor> getDonorDetails(@RequestParam Long donorId) {
		return userService.getDonorById(donorId);
	}

	@GetMapping("/recipient")
	public ResponseEntity<Recipient> getRecipientDetails(@RequestParam Long recipientId) {
		return userService.getRecipientById(recipientId);
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		User loggedInUser = userService.authenticate(user.getEmail(), user.getPassword());
		if (loggedInUser != null) {
			return ResponseEntity.ok(loggedInUser);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error registering user: " + e.getMessage());
		}
	}
}