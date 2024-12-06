package com.schemax.foodforward.service;

import com.schemax.foodforward.dto.CreateReviewDTO;
import com.schemax.foodforward.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    ResponseEntity<String> addReview(CreateReviewDTO createReviewDTO);
    ResponseEntity<List<Review>> getReviews(Long donorId);
}
