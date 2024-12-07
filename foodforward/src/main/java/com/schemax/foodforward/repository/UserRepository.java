package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Recipient;
import com.schemax.foodforward.model.User;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> findAllByLatitudeIsNullAndLongitudeIsNull() {
		String sql = "SELECT * FROM User WHERE latitude IS NULL AND longitude IS NULL";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			User user = new User();
			user.setUserId(rs.getLong("user_id"));
			user.setName(rs.getString("name"));
			user.setLocation(rs.getString("location"));
			user.setContactPreference(rs.getString("contact_preference"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setType(rs.getString("type"));
			return user;
		});
	}

	public Long updateUserLatitudeLongitude(Double latitude, Double longitude, Long userId) {
		String listingSql = "UPDATE User SET latitude = ?,  longitude = ? where user_id = ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(listingSql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, latitude);
			ps.setDouble(2, longitude);
			ps.setLong(3, userId);
			return ps;
		}, keyHolder);

		return userId;
	}

	@SuppressWarnings("deprecation")
	public User findById(Long userId) {
		String sql = "SELECT * FROM User WHERE user_id = ?";

		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { userId }, (rs, rowNum) -> {
				User user = new User();
				user.setUserId(rs.getLong("user_id"));
				user.setName(rs.getString("name"));
				user.setLocation(rs.getString("location"));
				user.setContactPreference(rs.getString("contact_preference"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setType(rs.getString("type"));
				user.setLatitude(rs.getDouble("latitude"));
				user.setLongitude(rs.getDouble("longitude"));
				return user;
			});
		} catch (EmptyResultDataAccessException e) {
			return null; // Return null if no user is found
		}
	}
	
	@SuppressWarnings("deprecation")
	public User findByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{email, password}, (rs, rowNum) ->
			new User(
				rs.getLong("user_id"),
				rs.getString("name"),
				rs.getString("location"),
				rs.getDouble("latitude"),
				rs.getDouble("longitude"),
				rs.getString("contact_preference"),
				rs.getString("email"),
				rs.getString("phone"),
				rs.getString("type")
			)
		);
	}
	
	public Long insertUser(User user) {
		try {
			String sql = """
					INSERT INTO User (name, location, email, phone, type, password)
					VALUES (?, ?, ?, ?, ?, ?)
					""";
	
			KeyHolder keyHolder = new GeneratedKeyHolder();
	
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setString(2, user.getLocation());
				ps.setString(3, user.getEmail());
				ps.setString(4, user.getPhone());
				ps.setString(5, user.getType());
				ps.setString(6, user.getPassword());
				return ps;
			}, keyHolder);
	
			return keyHolder.getKey().longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void insertDonor(Donor donor) {
		try {
			String sql = """
					INSERT INTO Donor (type, user_id)
					VALUES (?, ?, ?, ?)
					""";

			jdbcTemplate.update(sql, donor.getType(), donor.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertRecipient(Recipient recipient) {
		try {
			String sql = """
					INSERT INTO Recipient (notification_enabled, user_id)
					VALUES (?, ?, ?)
					""";

			jdbcTemplate.update(sql, recipient.isNotificationEnabled(), recipient.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
