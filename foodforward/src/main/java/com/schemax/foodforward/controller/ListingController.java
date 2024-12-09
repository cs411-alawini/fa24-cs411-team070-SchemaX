package com.schemax.foodforward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;
import com.schemax.foodforward.dto.UpdateListingDTO;
import com.schemax.foodforward.model.Item;
import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.service.ListingService;

@RestController
@RequestMapping("/listings")
public class ListingController {

	@Autowired
	private ListingService listingService;

	@PostMapping("/search")
	public List<Listing> searchListings(@RequestBody ListingSearchDTO searchDTO) {
		System.out.println(searchDTO);
		return listingService.searchListings(searchDTO);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addListing(@RequestBody CreateListingDTO listing) {
		return listingService.addListing(listing);
	}

	@GetMapping("/getListing")
	public ResponseEntity<Listing> getListingDetails(@RequestParam(required = false) Long listingId) {
		return listingService.getListingDetails(listingId);
	}

	@GetMapping("/getAllListings")
	public ResponseEntity<List<Listing>> getAllListings(@RequestParam(required = false) Long donorId) {
		return listingService.getAllListings(donorId);
	}

	@PostMapping("/updateListing")
	public ResponseEntity<String> updateListing(@RequestBody UpdateListingDTO updateListingDTO) {
		return listingService.updateListing(updateListingDTO);
	}

	@GetMapping("/items")
	public ResponseEntity<List<Item>> getItems(@RequestParam(required = false) String searchQuery) {
		return listingService.getItems(searchQuery);
	}

	@PostMapping("/item")
	public ResponseEntity<String> saveItem(@RequestBody Item createItem) {
		return listingService.saveItem(createItem);
	}

	@DeleteMapping("/delete/{listingId}")
	public ResponseEntity<String> deleteListing(@PathVariable Long listingId) {
		return listingService.deleteListing(listingId);
	}

	// TODO: update Listing Item
}