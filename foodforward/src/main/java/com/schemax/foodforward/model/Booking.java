package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "booked_by", foreignKey = @ForeignKey(name = "FK_booking_recipient"))
    private Recipient recipient;

    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    @Column(name = "pickup_datetime")
    private Date pickupDatetime;
}
