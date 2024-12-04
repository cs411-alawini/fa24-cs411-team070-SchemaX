package com.schemax.foodforward.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateListingDTO {
    private Long listingId;
    private String location;
    private Double latitude;
    private Double longitude;
    private String type;
    private String pickupTimeRange;
    private String status;
    private Date expirationDate;
}
