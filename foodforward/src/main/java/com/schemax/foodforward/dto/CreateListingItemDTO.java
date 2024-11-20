package com.schemax.foodforward.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateListingItemDTO {
    private Long itemId;
    private Date expirationDate;
    private Long quantity;
}
