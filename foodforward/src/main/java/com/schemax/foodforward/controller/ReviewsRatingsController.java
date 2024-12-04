package com.schemax.foodforward.controller;

import com.schemax.foodforward.dto.CreateReviewDTO;
import com.schemax.foodforward.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewsRatingsController {
    /**
     * TODO:
     *  - Add Reviews
     *  - Update Reviews
     *  - Fetch Reviews
     */
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody CreateReviewDTO createReviewDTO) {
        return reviewService.addReview(createReviewDTO);
    }

}
