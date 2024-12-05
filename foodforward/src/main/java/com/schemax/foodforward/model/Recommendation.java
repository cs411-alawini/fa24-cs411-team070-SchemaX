package com.schemax.foodforward.model;

import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matching_id")
	private Long matchingId;

	@Column(name = "type")
	private String type;

	@Column(name = "food_type")
	private String foodType;

	@ManyToOne
	@JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "FK_recommendation_recipient"))
	private Recipient recipient;

	@ManyToOne
	@JoinColumn(name = "donor_id", foreignKey = @ForeignKey(name = "FK_recommendation_donor"))
	private Donor donor;

	@Column(name = "score")
	private Double score;

	public Long getMatchingId() {
		return matchingId;
	}

	public void setMatchingId(Long matchingId) {
		this.matchingId = matchingId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
