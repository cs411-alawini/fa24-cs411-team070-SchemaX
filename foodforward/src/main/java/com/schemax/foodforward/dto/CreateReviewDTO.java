package com.schemax.foodforward.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateReviewDTO {
    private String review;
    private Date reviewDate;
    private Long rating;
    private Long donorId;
    private Long recipientId;
}
