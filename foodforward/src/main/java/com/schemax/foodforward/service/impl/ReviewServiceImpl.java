package com.schemax.foodforward.service.impl;

import com.schemax.foodforward.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.CreateReviewDTO;
import com.schemax.foodforward.repository.ReviewRepository;
import com.schemax.foodforward.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public ResponseEntity<String> addReview(CreateReviewDTO createReviewDTO) {
		try {
			Long reviewId = reviewRepository.addReview(createReviewDTO);
			return ResponseEntity.ok("Review Added successfully with ID: " + reviewId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding review: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<Review>> getReviews(Long donorId) {
		try {
			List<Review> reviews = reviewRepository.getReviews(donorId);
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
