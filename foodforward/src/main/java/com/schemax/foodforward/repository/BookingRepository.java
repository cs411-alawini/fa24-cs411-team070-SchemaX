package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.dto.CreateBookingDTO;

@Repository
public class BookingRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Long addBooking(CreateBookingDTO createBookingDTO) {
		String bookingSql = "INSERT INTO Booking(booked_by, booking_status, pickup_datetime) " + "VALUES (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, createBookingDTO.getBookedBy());
			ps.setString(2, createBookingDTO.getBookingStatus());
			ps.setDate(3, new java.sql.Date(createBookingDTO.getPickupDate().getTime()));
			return ps;
		}, keyHolder);

		Long bookingId = keyHolder.getKey().longValue();

		String listingItemSql = "UPDATE ListingItem set " + "booking_id = ? , " + "status = ? "
				+ "where listing_item_id = ?";

		for (Long listingId : createBookingDTO.getListingItemIds()) {
			jdbcTemplate.update(listingItemSql, bookingId, "BOOKED", listingId);
		}

		return bookingId;
	}

	public List<Map<String, Object>> findBookingDetailsById(Long bookingId) {
		String sql = """
				        SELECT
				            b.booking_id AS bookingId,
				            b.booking_status AS status,
				            b.pickup_datetime AS pickupDate,
				            l.location AS pickupLocation,
				            l.pickup_time_range AS pickupTime,
				            li.quantity,
				            li.expiration_date AS expirationDate,
				            i.item_name AS itemName,
				            u.name AS recipientName,
				            u.email AS email,
				            u.phone AS phoneNumber
				        FROM
				            Booking b
				                JOIN
				            Listing l ON b.booking_id = l.listing_id
				                JOIN
				            ListingItem li ON l.listing_id = li.listing_id
				                JOIN
				            Item i ON li.item_id = i.item_id
				                JOIN
				            Donor d ON l.listed_by = d.donor_id
				                JOIN
				            User u ON b.booked_by = u.user_id
				        WHERE
				            b.booking_id = ?
				""";

		return jdbcTemplate.queryForList(sql, bookingId);
	}
}
