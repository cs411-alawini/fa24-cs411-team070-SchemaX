package com.schemax.foodforward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Recipient;
import com.schemax.foodforward.model.User;
import com.schemax.foodforward.repository.DonorRepository;
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

	@Override
	public ResponseEntity<Recipient> getRecipientById(Long recipientId) {
		Recipient recipient = donorRepository.findRecipientById(recipientId);
		if (recipient != null) {
			return ResponseEntity.ok(recipient);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public User authenticate(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public void registerUser(User user) throws Exception {
		if ("Donor".equalsIgnoreCase(user.getType())) {
			userRepository.insertDonor(user);
		} else if ("Recipient".equalsIgnoreCase(user.getType())) {
			userRepository.insertRecipient(user);
		} else {
			throw new Exception("Invalid user type: " + user.getType());
		}
	}

}
