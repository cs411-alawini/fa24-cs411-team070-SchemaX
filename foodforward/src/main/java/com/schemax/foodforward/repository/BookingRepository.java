package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.schemax.foodforward.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.dto.CreateBookingDTO;

@Slf4j
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


	@SuppressWarnings("deprecation")
	public List<Booking> getRecipientBookings(Long recipientId) {
		String sql = "SELECT b.*, it.item_id, it.item_name, it.category, li.quantity, li.listing_item_id, li.expiration_date, l.listing_id, li.status, l.location, d.donor_id, u.name, u.phone, u.email " +
				"FROM Booking b " +
				"LEFT JOIN ListingItem li on b.booking_id = li.booking_id " +
				"LEFT JOIN Item it on li.item_id = it.item_id " +
				"LEFT JOIN Listing l on li.listing_id = l.listing_id " +
				"LEFT JOIN Donor d on l.listed_by = d.donor_id " +
				"LEFT JOIN User u on d.user_id = u.user_id " +
				"WHERE b.booked_by = ?";

		Map<Long, Booking> bookingMap = new HashMap<>();

		jdbcTemplate.query(sql, new Object[] { recipientId }, (rs) -> {
			Long currentListingId = rs.getLong("listing_id");
			Booking booking = bookingMap.computeIfAbsent(currentListingId, k -> {
				try {
					Booking b = new Booking();
					b.setBookingStatus( rs.getString("booking_status"));
					b.setBookingId(rs.getLong("booking_id"));
					b.setDonorId(rs.getLong("donor_id"));
					b.setBookedBy(rs.getLong("booked_by"));
					b.setName(rs.getString("name"));
					b.setEmail(rs.getString("email"));
					b.setPhone(rs.getString("phone"));
					b.setPickupDatetime(rs.getDate("pickup_datetime"));
					b.setListingItems(new ArrayList<>());
					return b;
				} catch (SQLException e) {
					log.error("Exception while ");
					throw new RuntimeException(e);
				}
			});

			Long listingItemId = rs.getLong("listing_item_id");
			if (listingItemId != 0) {
				ListingItem listingItem = new ListingItem();
				listingItem.setListingId(rs.getLong("listing_id"));
				listingItem.setListingItemId(listingItemId);
				listingItem.setQuantity(rs.getLong("quantity"));
				listingItem.setExpirationDate(rs.getDate("expiration_date"));
				listingItem.setStatus(rs.getString("status"));
				listingItem.setBookingId(rs.getLong("booking_id"));

				Item itemDetails = new Item();
				itemDetails.setItemId(rs.getLong("item_id"));
				itemDetails.setItemName(rs.getString("item_name"));
				itemDetails.setCategory(rs.getString("category"));
				listingItem.setItem(itemDetails);
				booking.getListingItems().add(listingItem);
			}
		});

		return new ArrayList<>(bookingMap.values());

	}

	@SuppressWarnings("deprecation")
	public List<Booking> getDonorBookings(Long donorId) {
		String sql = "SELECT b.*, it.item_id, it.item_name, it.category, l.listed_by, li.quantity, li.listing_item_id, li.expiration_date, li.status, l.listing_id, l.location, u.name, u.phone, u.email " +
				"FROM Booking b " +
				"LEFT JOIN ListingItem li on b.booking_id = li.booking_id " +
				"LEFT JOIN Item it on li.item_id = it.item_id " +
				"LEFT JOIN Listing l on li.listing_id = l.listing_id " +
				"LEFT JOIN Donor d on l.listed_by = d.donor_id " +
				"LEFT JOIN Recipient r on b.booked_by = r.recipient_id " +
				"LEFT JOIN User u on r.user_id = u.user_id " +
				"WHERE l.listed_by = ?";

		Map<Long, Booking> bookingMap = new HashMap<>();

		jdbcTemplate.query(sql, new Object[] { donorId }, (rs) -> {
			Long currentListingId = rs.getLong("listing_id");
			Booking booking = bookingMap.computeIfAbsent(currentListingId, k -> {
				try {
					Booking b = new Booking();
					b.setBookingStatus( rs.getString("booking_status"));
					b.setBookingId(rs.getLong("booking_id"));
					b.setDonorId(rs.getLong("listed_by"));
					b.setBookedBy(rs.getLong("booked_by"));
					b.setName(rs.getString("name"));
					b.setEmail(rs.getString("email"));
					b.setPhone(rs.getString("phone"));
					b.setPickupDatetime(rs.getDate("pickup_datetime"));
					b.setListingItems(new ArrayList<>());
					return b;
				} catch (SQLException e) {
					log.error("Exception while ");
					throw new RuntimeException(e);
				}
			});

			Long listingItemId = rs.getLong("listing_item_id");
			if (listingItemId != 0) {
				ListingItem listingItem = new ListingItem();
				listingItem.setListingId(rs.getLong("listing_id"));
				listingItem.setListingItemId(listingItemId);
				listingItem.setQuantity(rs.getLong("quantity"));
				listingItem.setExpirationDate(rs.getDate("expiration_date"));
				listingItem.setStatus(rs.getString("status"));
				listingItem.setBookingId(rs.getLong("booking_id"));

				Item itemDetails = new Item();
				itemDetails.setItemId(rs.getLong("item_id"));
				itemDetails.setItemName(rs.getString("item_name"));
				itemDetails.setCategory(rs.getString("category"));
				listingItem.setItem(itemDetails);
				booking.getListingItems().add(listingItem);
			}
		});

		return new ArrayList<>(bookingMap.values());

	}
}