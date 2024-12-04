package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class Recipient extends User {

    private Long recipientId;

    private String preferences;

    private Boolean notificationEnabled;
}
