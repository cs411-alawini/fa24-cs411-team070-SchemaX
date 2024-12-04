package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Donor extends User {

	private Long donorId;

	private String type;

	private String preferredPickupTime;

	private Double rating;

	public Long getDonorId() {
		return donorId;
	}

	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPreferredPickupTime() {
		return preferredPickupTime;
	}

	public void setPreferredPickupTime(String preferredPickupTime) {
		this.preferredPickupTime = preferredPickupTime;
	}

}
