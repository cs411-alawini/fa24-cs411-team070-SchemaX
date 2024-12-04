package com.schemax.foodforward.dto;

import lombok.Data;


import java.sql.Date;
import java.util.List;

@Data
public class CreateBookingDTO {
    private Long bookedBy;
    private String bookingStatus;
    private List<Long> listingItemIds;
    private Date pickupDate;
}
