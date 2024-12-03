package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "Listing")
@Data
public class Listing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "listing_id")
	private Long listingId;

	@ManyToOne
	@JoinColumn(name = "listed_by", foreignKey = @ForeignKey(name = "FK_listing_donor"))
	private Donor donor;

	@Column(name = "location", length = 2048)
	private String location;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "type")
	private String type;

	@Column(name = "pickup_time_range")
	private String pickupTimeRange;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
	private List<ListingItem> listingItems;

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
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