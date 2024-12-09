package com.schemax.foodforward.service;

import java.util.List;
import java.util.Map;

import com.schemax.foodforward.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.CreateBookingDTO;

@Service
public interface BookingService {
	ResponseEntity<String> addBooking(CreateBookingDTO createBookingDTO);

	public Map<String, List<Map<String, Object>>> getBookingDetails(Long bookingId);
	ResponseEntity<List<Booking>> getDonorBookings(Long donorId);
	ResponseEntity<List<Booking>> getRecipientBookings(Long recipientId);
}
