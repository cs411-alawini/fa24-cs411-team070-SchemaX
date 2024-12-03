package com.schemax.foodforward.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateListingItemDTO {
	private Long itemId;
	private Date expirationDate;
	private Long quantity;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
