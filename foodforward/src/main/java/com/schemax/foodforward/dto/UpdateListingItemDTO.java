package com.schemax.foodforward.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateListingItemDTO {
    private Long itemId;
    private Date expirationDate;
    private Long quantity;
    private String status;
}
