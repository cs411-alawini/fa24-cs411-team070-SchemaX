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

}