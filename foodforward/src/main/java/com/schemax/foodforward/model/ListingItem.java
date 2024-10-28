package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ListingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingItemId;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item itemId;
    @OneToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listingId;
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking bookingId;
    private String status;

}
