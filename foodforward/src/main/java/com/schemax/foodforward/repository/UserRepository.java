package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Recipient;
import com.schemax.foodforward.model.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

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
		String sql = """
				    SELECT
				        u.user_id AS userId,
				        u.name AS name,
				        u.location AS location,
				        u.latitude AS latitude,
				        u.longitude AS longitude,
				        u.contact_preference AS contactPreference,
				        u.email AS email,
				        u.phone AS phone,
				        u.type AS type,
				        d.donor_id AS donorId,
				        r.recipient_id AS recipientId
				    FROM
				        User u
				    LEFT JOIN
				        Donor d ON u.user_id = d.user_id
				    LEFT JOIN
				        Recipient r ON u.user_id = r.user_id
				    WHERE
				        u.email = ? AND u.password = ?
				""";

		return jdbcTemplate.queryForObject(sql, new Object[] { email, password }, (rs, rowNum) -> {
			User user = new User();
			user.setUserId(rs.getLong("userId"));
			user.setName(rs.getString("name"));
			user.setLocation(rs.getString("location"));
			user.setLatitude(rs.getDouble("latitude"));
			user.setLongitude(rs.getDouble("longitude"));
			user.setContactPreference(rs.getString("contactPreference"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setType(rs.getString("type"));

			if ("Donor".equalsIgnoreCase(user.getType())) {
				user.setDonorId(rs.getLong("donorId"));
			} else if ("Recipient".equalsIgnoreCase(user.getType())) {
				user.setRecipientId(rs.getLong("recipientId"));
			}

			return user;
		});
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


	public Long insertDonor(User user) {
			TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
			return transactionTemplate.execute(status -> {
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

					Long userId =  keyHolder.getKey().longValue();

					String sqlDonor = """
					INSERT INTO Donor (type, user_id)
					VALUES (?, ?)
					""";

					KeyHolder keyHolderDonor = new GeneratedKeyHolder();

					jdbcTemplate.update(con-> {
						PreparedStatement ps = con.prepareStatement(sqlDonor, Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, user.getType());
						ps.setLong(2, userId);
						return ps;
					}, keyHolderDonor);

					return keyHolderDonor.getKey().longValue();
				} catch (Exception e) {
					log.error("Error creating Donor");
					throw new RuntimeException("Error creating Donor", e);
				}
			});
	}

	public Long insertRecipient(User user) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		return transactionTemplate.execute(status -> {
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

				Long userId =  keyHolder.getKey().longValue();

				String sqlRecipient = """
					INSERT INTO Recipient (notification_enabled, user_id)
					VALUES (?, ?)
					""";

				KeyHolder keyHolderRecipient = new GeneratedKeyHolder();

				jdbcTemplate.update(con-> {
					PreparedStatement ps = con.prepareStatement(sqlRecipient, Statement.RETURN_GENERATED_KEYS);
					ps.setBoolean(1, ((Recipient) user).isNotificationEnabled());
					ps.setLong(2, userId);
					return ps;
				}, keyHolderRecipient);

				return keyHolderRecipient.getKey().longValue();
			} catch (Exception e) {
				log.error("Error creating Recipient");
				throw new RuntimeException("Error creating Recipient", e);
			}
		});
	}
}
