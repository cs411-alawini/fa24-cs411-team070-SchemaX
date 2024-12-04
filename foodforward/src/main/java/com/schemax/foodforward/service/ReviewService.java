package com.schemax.foodforward.service;

import com.schemax.foodforward.dto.CreateReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    ResponseEntity<String> addReview(CreateReviewDTO createReviewDTO);
}
