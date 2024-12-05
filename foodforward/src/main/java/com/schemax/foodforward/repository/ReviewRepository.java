package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.dto.CreateReviewDTO;

@Repository
public class ReviewRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Long addReview(CreateReviewDTO createReviewDTO) {

		String bookingSql = "INSERT INTO Review(review, review_date, rating, donor_id, recipient_id) "
				+ "VALUES (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, createReviewDTO.getReview());
			ps.setDate(2, new java.sql.Date(createReviewDTO.getReviewDate().getTime()));
			ps.setLong(3, createReviewDTO.getRating());
			ps.setLong(4, createReviewDTO.getDonorId());
			ps.setLong(5, createReviewDTO.getRecipientId());
			return ps;
		}, keyHolder);

		Long reviewId = keyHolder.getKey().longValue();

		return reviewId;
	}
}
