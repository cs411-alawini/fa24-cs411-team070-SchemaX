package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
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

	public Map<String, List<Map<String, Object>>> getBookingDetails(Long bookingId) {
		Map<String, List<Map<String, Object>>> responseData = new HashMap<>();

		jdbcTemplate.execute("CALL GetBookingDetails(?)", (CallableStatementCallback<Void>) cs -> {
			cs.setLong(1, bookingId);

			try (ResultSet rs = cs.executeQuery()) {
				List<Map<String, Object>> mainQuery = new ArrayList<>();
				while (rs.next()) {
					Map<String, Object> mainDetails = new HashMap<>();
					mainDetails.put("itemName", rs.getString("itemName"));
					mainDetails.put("quantity", rs.getLong("quantity"));
					mainDetails.put("expirationDate", rs.getDate("expirationDate"));
					mainDetails.put("recipientName", rs.getString("recipientName"));
					mainDetails.put("email", rs.getString("email"));
					mainDetails.put("phoneNumber", rs.getString("phoneNumber"));
					mainDetails.put("pickupDate", rs.getTimestamp("pickupDate"));
					mainDetails.put("pickupTime", rs.getString("pickupTime"));
					mainDetails.put("pickupLocation", rs.getString("pickupLocation"));
					mainDetails.put("status", rs.getString("status"));
					mainQuery.add(mainDetails);
				}
				responseData.put("mainQuery", mainQuery);
			}

			if (cs.getMoreResults()) {
				try (ResultSet rs = cs.getResultSet()) {
					List<Map<String, Object>> itemSummary = new ArrayList<>();
					while (rs.next()) {
						Map<String, Object> summary = new HashMap<>();
						summary.put("totalItems", rs.getInt("totalItems"));
						summary.put("totalQuantity", rs.getInt("totalQuantity"));
						summary.put("latestExpirationDate", rs.getDate("latestExpirationDate"));
						itemSummary.add(summary);
					}
					responseData.put("itemSummary", itemSummary);
				}
			}

			if (cs.getMoreResults()) {
				try (ResultSet rs = cs.getResultSet()) {
					List<Map<String, Object>> donorDetails = new ArrayList<>();
					while (rs.next()) {
						Map<String, Object> donorInfo = new HashMap<>();
						donorInfo.put("donorId", rs.getLong("donorId"));
						donorInfo.put("donorName", rs.getString("donorName"));
						donorInfo.put("donorEmail", rs.getString("donorEmail"));
						donorInfo.put("donorPhone", rs.getString("donorPhone"));
						donorInfo.put("averageRating", rs.getDouble("averageRating"));
						donorInfo.put("totalReviews", rs.getInt("totalReviews"));
						donorDetails.add(donorInfo);
					}
					responseData.put("donorDetails", donorDetails);
				}
			}

			return null;
		});

		return responseData;
	}
}