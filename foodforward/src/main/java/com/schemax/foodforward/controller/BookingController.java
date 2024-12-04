package com.schemax.foodforward.controller;

import com.schemax.foodforward.dto.CreateBookingDTO;
import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    /**
     * TODO:
     *  - BookListing by Recipient
     *  - Get all bookings by recipient
     *  - Cancel Booking/Update Booking
     *  - Get a single booking detail
     */

    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<String> bookListing(@RequestBody CreateBookingDTO createBookingDTO) {
        return bookingService.addBooking(createBookingDTO);
    }

}
