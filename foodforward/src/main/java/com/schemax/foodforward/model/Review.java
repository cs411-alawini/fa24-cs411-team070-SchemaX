package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    @Column(name = "review_date")
    private Date reviewDate;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "donor_id", foreignKey = @ForeignKey(name = "FK_review_donor"))
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "FK_review_recipient"))
    private Recipient recipient;
}
