package com.schemax.foodforward.model;

import java.util.Date;

import lombok.Data;

@Data
public class Review {
	private Long reviewId;
	private String review;
	private Date reviewDate;
	private Integer rating;
	private Long donorId;
	private Long recipientId;
	private String recipientName;
}
