package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    private String status; // e.g., "Requested", "Approved", "Completed"
    private String pickupDateTime;

}
