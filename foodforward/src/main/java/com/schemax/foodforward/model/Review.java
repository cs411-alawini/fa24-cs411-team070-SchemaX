package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Review {

    private Long reviewId;

    private String review;

    private Date reviewDate;

    private Integer rating;

    private Donor donor;

    private Recipient recipient;
}
