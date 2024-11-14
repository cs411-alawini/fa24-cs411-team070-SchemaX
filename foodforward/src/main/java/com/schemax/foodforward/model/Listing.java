package com.schemax.foodforward.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "Listing")
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

    @Column(name = "type")
    private String type;

    @Column(name = "pickup_time_range")
    private String pickupTimeRange;

    @Column(name = "status")
    private String status;
}