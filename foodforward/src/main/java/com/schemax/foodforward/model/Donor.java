package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long donorId;

    @Column(name = "type")
    private String type;

    @Column(name = "preferred_pickup_time")
    private String preferredPickupTime;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_donor_user"))
    private User user;

}
