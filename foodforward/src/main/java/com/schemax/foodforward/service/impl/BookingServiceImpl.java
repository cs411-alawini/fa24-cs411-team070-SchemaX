package com.schemax.foodforward.service.impl;


import com.schemax.foodforward.dto.CreateBookingDTO;
import com.schemax.foodforward.repository.BookingRepository;
import com.schemax.foodforward.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}