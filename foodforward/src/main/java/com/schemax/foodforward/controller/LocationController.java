package com.schemax.foodforward.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping("/update-listing-coordinates")
	public String updateListingCoordinates() {
		locationService.updateListingsWithCoordinates();
		return "Locations updated successfully!";
	}

	@PostMapping("/update-user-coordinates")
	public String updateUserCoordinates() {
		locationService.updateUsersWithCoordinates();
		return "Users updated successfully!";
	}
}