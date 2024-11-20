package com.schemax.foodforward.controller;

import java.util.List;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;
import com.schemax.foodforward.service.ListingService;

@RestController
@RequestMapping("/listings")
public class ListingController {

	@Autowired
	private ListingService listingService;

	@PostMapping("/search")
	public List<ListingDTO> searchListings(@RequestBody ListingSearchDTO searchDTO) {
		return listingService.searchListings(searchDTO);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addListing(@RequestBody CreateListingDTO listing) {
		return listingService.addListing(listing);
	}

	@GetMapping("/getListing")
	public ResponseEntity<Listing> getListingDetails(@RequestParam Long listingId) {
		return listingService.getListingDetails(listingId);
	}
}