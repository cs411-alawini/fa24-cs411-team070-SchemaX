package com.schemax.foodforward.controller;

import com.schemax.foodforward.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schemax.foodforward.dto.CreateReviewDTO;
import com.schemax.foodforward.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsRatingsController {
	/**
	 * TODO: - Add Reviews - Update Reviews - Fetch Reviews
	 */
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/add")
	public ResponseEntity<String> addReview(@RequestBody CreateReviewDTO createReviewDTO) {
		return reviewService.addReview(createReviewDTO);
	}

	@GetMapping("/getReviews")
	public ResponseEntity<List<Review>> getReviews(@RequestParam Long donorId) {
		return reviewService.getReviews(donorId);
	}
}
