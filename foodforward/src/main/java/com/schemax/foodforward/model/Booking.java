package com.schemax.foodforward.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Booking {

    private Long bookingId;

    private Long bookedBy;

    private String bookingStatus;

    private Date pickupDatetime;
}
