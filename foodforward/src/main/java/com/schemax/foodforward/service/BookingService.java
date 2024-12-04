package com.schemax.foodforward.service;

import com.schemax.foodforward.dto.CreateBookingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    ResponseEntity<String> addBooking(CreateBookingDTO createBookingDTO);

}
