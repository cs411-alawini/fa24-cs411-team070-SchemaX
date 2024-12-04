package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;
    private String channel; // e.g., "Email", "SMS", "App"
    private LocalDateTime sentAt;

}
