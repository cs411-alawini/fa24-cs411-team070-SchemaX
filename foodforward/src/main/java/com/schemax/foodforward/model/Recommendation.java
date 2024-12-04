package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

//@Entity
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long matchingId;

    @Column(name = "type")
    private String type;

    @Column(name = "food_type")
    private String foodType;

    @ManyToOne
    @JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "FK_recommendation_recipient"))
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name = "donor_id", foreignKey = @ForeignKey(name = "FK_recommendation_donor"))
    private Donor donor;

    @Column(name = "score")
    private Double score;

}
