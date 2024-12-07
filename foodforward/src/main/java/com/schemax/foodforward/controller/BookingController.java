package com.schemax.foodforward.controller;

import java.util.List;
import java.util.Map;

import com.schemax.foodforward.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<Map<String, List<Map<String, Object>>>> getBookingDetails(@PathVariable Long id) {
		Map<String, List<Map<String, Object>>> details = bookingService.getBookingDetails(id);

		if (!details.isEmpty()) {
			return ResponseEntity.ok(details);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/getDonorBookings")
	public ResponseEntity<List<Booking>> getDonorBookings(@RequestParam Long donorId) {
		return bookingService.getDonorBookings(donorId);

	}

	@GetMapping("/getRecipientBookings")
	public ResponseEntity<List<Booking>> getRecipientBookings(@RequestParam Long recipientId) {
		return bookingService.getRecipientBookings(recipientId);
	}
}
