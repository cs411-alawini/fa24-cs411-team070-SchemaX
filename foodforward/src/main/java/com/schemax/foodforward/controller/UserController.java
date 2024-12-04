package com.schemax.foodforward.controller;

import java.util.Optional;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}