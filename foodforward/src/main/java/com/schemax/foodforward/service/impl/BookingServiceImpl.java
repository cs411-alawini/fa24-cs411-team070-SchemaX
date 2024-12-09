package com.schemax.foodforward.service.impl;

import java.util.List;
import java.util.Map;

import com.schemax.foodforward.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.CreateBookingDTO;
import com.schemax.foodforward.repository.BookingRepository;
import com.schemax.foodforward.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Override
	public ResponseEntity<String> addBooking(CreateBookingDTO createBookingDTO) {
		try {
			Long bookingId = bookingRepository.addBooking(createBookingDTO);
			return ResponseEntity.ok("Booked successfully with ID: " + bookingId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating listing: " + e.getMessage());
		}
	}

	@Override
	public Map<String, List<Map<String, Object>>> getBookingDetails(Long bookingId) {
		return bookingRepository.getBookingDetails(bookingId);
	}

	@Override
	public ResponseEntity<List<Booking>> getDonorBookings(Long donorId) {
		List<Booking> details = bookingRepository.getDonorBookings(donorId);

		if (!details.isEmpty()) {
			return ResponseEntity.ok(details);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<Booking>> getRecipientBookings(Long recipientId) {
		List<Booking> details = bookingRepository.getRecipientBookings(recipientId);

		if (!details.isEmpty()) {
			return ResponseEntity.ok(details);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
