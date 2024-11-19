package com.schemax.foodforward.dto;

import com.schemax.foodforward.model.ListingItem;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateListingDTO {
    private Long donorId;
    private String location;
    private Double latitude;
    private Double longitude;
    private String type;
    private String pickupTimeRange;
    private Date expirationDate;
    private List<CreateListingItemDTO> listingItems;
}
