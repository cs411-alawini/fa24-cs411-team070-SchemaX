package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorId;
    private String type;
    private String preferredPickupTime;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

}
