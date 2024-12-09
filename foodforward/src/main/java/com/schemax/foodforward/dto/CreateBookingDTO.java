package com.schemax.foodforward.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class CreateBookingDTO {
	private Long bookedBy;
	private String bookingStatus;
	private List<Long> listingItemIds;
	private Date pickupDate;

	public Long getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(Long bookedBy) {
		this.bookedBy = bookedBy;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public List<Long> getListingItemIds() {
		return listingItemIds;
	}

	public void setListingItemIds(List<Long> listingItemIds) {
		this.listingItemIds = listingItemIds;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

}
