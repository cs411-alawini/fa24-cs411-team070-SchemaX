package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    private String foodType;
    private int quantity;
    private String unitOfMeasure;
    private String expirationDate;
    private String storageType;
    private String status;
}