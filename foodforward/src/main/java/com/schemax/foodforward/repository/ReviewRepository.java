package com.schemax.foodforward.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.model.Review;
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

	public List<Review> getReviews(Long donorId) {

		String sql = """
				   SELECT
       					r.*,
				        u.name AS recipient_name
				    FROM Review r
				    LEFT JOIN Recipient recip ON r.recipient_id = recip.recipient_id
				    JOIN User u ON recip.user_id = u.user_id
				    WHERE
				        r.donor_id = ?;
				""";

		return jdbcTemplate.query(sql,
				new Object[] {donorId },
				(rs, rowNum) -> {
					Review dto = new Review();
					dto.setDonorId(rs.getLong("donor_id"));
					dto.setRating(rs.getInt("rating"));
					dto.setRecipientId(rs.getLong("recipient_id"));
					dto.setRecipientName(rs.getString("recipient_name"));
					dto.setReviewId(rs.getLong("review_id"));
					dto.setReview(rs.getString("review"));
					dto.setReviewDate(rs.getDate("review_date"));
					return dto;
				});
	}
}
