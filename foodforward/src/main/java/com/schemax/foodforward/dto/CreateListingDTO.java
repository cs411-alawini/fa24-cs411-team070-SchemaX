package com.schemax.foodforward.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateListingDTO {
	private Long donorId;
	private String location;
	private Double latitude;
	private Double longitude;
	private String type;
	private String pickupTimeRange;
	private String status;
	private List<CreateListingItemDTO> listingItems;

	public Long getDonorId() {
		return donorId;
	}

	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPickupTimeRange() {
		return pickupTimeRange;
	}

	public void setPickupTimeRange(String pickupTimeRange) {
		this.pickupTimeRange = pickupTimeRange;
	}

	public List<CreateListingItemDTO> getListingItems() {
		return listingItems;
	}

	public void setListingItems(List<CreateListingItemDTO> listingItems) {
		this.listingItems = listingItems;
	}

}
