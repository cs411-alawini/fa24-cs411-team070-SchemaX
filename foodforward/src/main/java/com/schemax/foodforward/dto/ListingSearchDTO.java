package com.schemax.foodforward.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ListingSearchDTO {
	private String recipientId;
	private String foodType;
	private Long quantityNeeded;
	private Date expiryDate;
	private String pickupTimeStart;
	private String pickupTimeEnd;
	private String location;
	private Double distance;
	private Double userLatitude;
	private Double userLongitude;

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public Long getQuantityNeeded() {
		return quantityNeeded;
	}

	public void setQuantityNeeded(Long quantityNeeded) {
		this.quantityNeeded = quantityNeeded;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPickupTimeStart() {
		return pickupTimeStart;
	}

	public void setPickupTimeStart(String pickupTimeStart) {
		this.pickupTimeStart = pickupTimeStart;
	}

	public String getPickupTimeEnd() {
		return pickupTimeEnd;
	}

	public void setPickupTimeEnd(String pickupTimeEnd) {
		this.pickupTimeEnd = pickupTimeEnd;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(Double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public Double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(Double userLongitude) {
		this.userLongitude = userLongitude;
	}

	@Override
	public String toString() {
		return "ListingSearchDTO [foodType=" + foodType + ", quantityNeeded=" + quantityNeeded + ", expiryDate="
				+ expiryDate + ", pickupTimeStart=" + pickupTimeStart + ", pickupTimeEnd=" + pickupTimeEnd
				+ ", location=" + location + ", distance=" + distance + ", userLatitude=" + userLatitude
				+ ", userLongitude=" + userLongitude + ", recepientId=" + recipientId + "]";
	}

}