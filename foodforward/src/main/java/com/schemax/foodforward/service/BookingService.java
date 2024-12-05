package com.schemax.foodforward.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.CreateBookingDTO;

@Service
public interface BookingService {
	ResponseEntity<String> addBooking(CreateBookingDTO createBookingDTO);

	public List<Map<String, Object>> getBookingDetails(Long bookingId);
}
