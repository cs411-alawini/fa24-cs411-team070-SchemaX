package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipientId;
    private String currentRequirements;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
