package com.schemax.foodforward.repository;


import com.schemax.foodforward.model.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.model.Donor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DonorRepository {
	// Create Donor
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	public Donor findDonorById(Long donorId) {
		String sql = "SELECT d.donor_id, d.type, d.preferred_pickup_time, u.*, avg_rating " + "FROM Donor d "
				+ "LEFT JOIN User u ON d.user_id = u.user_id " + "LEFT JOIN ("
				+ "SELECT donor_id, avg(RATING) as avg_rating "
				+ "from Review where donor_id = ? group by donor_id) r ON r.donor_id = d.donor_id "
				+ "WHERE d.donor_id = ?";

		log.info("Finding Donor Details for donor id {} : {}", donorId, sql);

		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { donorId, donorId }, (rs, rowNum) -> {
				Donor donor = new Donor();
				donor.setDonorId(rs.getLong("donor_id"));
				donor.setType(rs.getString("type"));
				donor.setPreferredPickupTime(rs.getString("preferred_pickup_time"));
				donor.setRating(rs.getDouble("avg_rating"));
				donor.setUserId(rs.getLong("user_id"));
				donor.setName(rs.getString("name"));
				donor.setLocation(rs.getString("location"));
				donor.setContactPreference(rs.getString("contact_preference"));
				donor.setEmail(rs.getString("email"));
				donor.setPhone(rs.getString("phone"));
				donor.setType(rs.getString("type"));

				return donor;
			});
		} catch (EmptyResultDataAccessException e) {
			return null; // Return null if no donor is found
		}
	}

	@SuppressWarnings("deprecation")
	public Recipient findRecipientById(Long recipientId) {
		String sql = "SELECT r.recipient_id, r.preferences, r.notification_enabled, u.* " +
				"FROM Recipient r "
				+ "LEFT JOIN User u ON r.user_id = u.user_id "
				+ "WHERE r.recipient_id = ?";

		log.info("Finding Recipient Details for recipient id {} : {}", recipientId, sql);

		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{recipientId}, (rs, rowNum) -> {
				Recipient recipient = new Recipient();
				recipient.setRecipientId(rs.getLong("recipient_id"));
				recipient.setType(rs.getString("preferences"));
				recipient.setNotificationEnabled(rs.getBoolean("notification_enabled"));
				recipient.setUserId(rs.getLong("user_id"));
				recipient.setName(rs.getString("name"));
				recipient.setLocation(rs.getString("location"));
				recipient.setContactPreference(rs.getString("contact_preference"));
				recipient.setEmail(rs.getString("email"));
				recipient.setPhone(rs.getString("phone"));
				recipient.setType(rs.getString("type"));

				return recipient;
			});
		} catch (EmptyResultDataAccessException e) {
			return null; // Return null if no donor is found
		}
	}

}
