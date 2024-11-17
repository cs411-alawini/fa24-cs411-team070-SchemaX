package com.schemax.foodforward.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.User;

@Service
public interface UserService {
	public Optional<User> getUserById(Long userId);
}