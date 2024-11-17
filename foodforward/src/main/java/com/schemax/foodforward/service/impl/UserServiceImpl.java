package com.schemax.foodforward.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.User;
import com.schemax.foodforward.repository.UserRepository;
import com.schemax.foodforward.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

}
