package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Donor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donor_id")
	private Long donorId;

	@Column(name = "type")
	private String type;

	@Column(name = "preferred_pickup_time")
	private String preferredPickupTime;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_donor_user"))
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
