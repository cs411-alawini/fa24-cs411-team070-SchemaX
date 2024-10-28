package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String review;
    private Date reviewDate;
    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donorId;
    @OneToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipientId;
}
