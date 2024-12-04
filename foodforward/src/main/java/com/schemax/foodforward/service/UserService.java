package com.schemax.foodforward.service;

import java.util.Optional;

import com.schemax.foodforward.model.Donor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.User;

@Service
public interface UserService {
	public User getUserById(Long userId);
	public ResponseEntity<Donor> getDonorById(Long donorId);
}