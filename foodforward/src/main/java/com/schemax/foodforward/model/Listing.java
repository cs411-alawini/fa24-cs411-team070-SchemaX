package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "listed_by", foreignKey = @ForeignKey(name = "FK_listing_donor"))
    private Donor donor;

    @Column(name = "location", length = 2048)
    private String location;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "type")
    private String type;

    @Column(name = "pickup_time_range")
    private String pickupTimeRange;

    @Column(name = "status")
    private String status;
}