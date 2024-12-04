package com.schemax.foodforward.service.impl;

import java.util.Optional;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.User;
import com.schemax.foodforward.repository.UserRepository;
import com.schemax.foodforward.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DonorRepository donorRepository;

	public User getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public ResponseEntity<Donor> getDonorById(Long donorId) {
		Donor donor = donorRepository.findDonorById(donorId);
		if (donor != null) {
			return ResponseEntity.ok(donor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
