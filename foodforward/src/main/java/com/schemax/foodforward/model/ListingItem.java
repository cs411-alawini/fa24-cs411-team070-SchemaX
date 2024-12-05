package com.schemax.foodforward.model;

import java.util.Date;

import lombok.ToString;

@ToString
public class ListingItem {
	private Long listingItemId;

	private Long quantity;

	private Item item;

	private Long listingId;

	private Long bookingId;

	private Date expirationDate;

	private String status;

	public Long getListingItemId() {
		return listingItemId;
	}

	public void setListingItemId(Long listingItemId) {
		this.listingItemId = listingItemId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
