package com.schemax.foodforward.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Booking {

	private Long bookingId;
	private Long donorId;

	private Long bookedBy;

	private String bookingStatus;
	private String name;
	private String phone;
	private String email;

	private Date pickupDatetime;

	private List<ListingItem> listingItems;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

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

	public Date getPickupDatetime() {
		return pickupDatetime;
	}

	public void setPickupDatetime(Date pickupDatetime) {
		this.pickupDatetime = pickupDatetime;
	}

}
