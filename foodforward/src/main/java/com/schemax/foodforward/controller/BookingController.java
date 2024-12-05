package com.schemax.foodforward.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.dto.CreateBookingDTO;
import com.schemax.foodforward.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	/**
	 * TODO: - BookListing by Recipient - Get all bookings by recipient - Cancel
	 * Booking/Update Booking - Get a single booking detail
	 */

	@Autowired
	private BookingService bookingService;

	@PostMapping("/add")
	public ResponseEntity<String> bookListing(@RequestBody CreateBookingDTO createBookingDTO) {
		return bookingService.addBooking(createBookingDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<Map<String, Object>>> getBookingDetails(@PathVariable Long id) {
		List<Map<String, Object>> bookingDetails = bookingService.getBookingDetails(id);
		if (!bookingDetails.isEmpty()) {
			return ResponseEntity.ok(bookingDetails);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
