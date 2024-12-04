package com.schemax.foodforward.repository;

import com.schemax.foodforward.dto.CreateBookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BookingRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long addBooking(CreateBookingDTO createBookingDTO) {
        String bookingSql = "INSERT INTO Booking(booked_by, booking_status, pickup_datetime) " +
                "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, createBookingDTO.getBookedBy());
            ps.setString(2, createBookingDTO.getBookingStatus());
            ps.setDate(3, new java.sql.Date(createBookingDTO.getPickupDate().getTime()));
            return ps;
        }, keyHolder);

        Long bookingId = keyHolder.getKey().longValue();

        String listingItemSql = "UPDATE ListingItem set " +
                "booking_id = ? , " +
                "status = ? " +
                "where listing_item_id = ?";

        for (Long listingId : createBookingDTO.getListingItemIds()) {
            jdbcTemplate.update(listingItemSql,
                    bookingId,
                     "BOOKED",
                     listingId
            );
        }

        return bookingId;
    }
}
