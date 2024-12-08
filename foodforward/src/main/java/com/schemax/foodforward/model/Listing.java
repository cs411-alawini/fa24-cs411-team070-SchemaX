package com.schemax.foodforward.model;

import java.util.List;

import com.schemax.foodforward.dto.ListingDTO;

import lombok.ToString;

@ToString
public class Listing extends ListingDTO {

	private Long listingId;

	private Donor donor;

	private String location;

	private Double latitude;

	private Double longitude;

	private String type;

	private String pickupTimeRange;

	private String status;

	private List<ListingItem> listingItems;
	private Long priority;

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ListingItem> getListingItems() {
		return listingItems;
	}

	public void setListingItems(List<ListingItem> listingItems) {
		this.listingItems = listingItems;
	}

}