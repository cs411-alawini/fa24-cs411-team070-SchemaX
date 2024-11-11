package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "preferences", length = 2048)
    private String preferences;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_recipient_user"))
    private User user;

    @Column(name = "notification_enabled")
    private Boolean notificationEnabled;
}
