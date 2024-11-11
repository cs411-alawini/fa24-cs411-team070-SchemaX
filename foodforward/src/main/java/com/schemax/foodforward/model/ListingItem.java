package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ListingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_item_id")
    private Long listingItemId;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_listingitem_item"))
    private Item item;

    @ManyToOne
    @JoinColumn(name = "listing_id", foreignKey = @ForeignKey(name = "FK_listingitem_listing"))
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "FK_listingitem_booking"))
    private Booking booking;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "status")
    private String status;

}
