package com.schemax.foodforward.service;

import java.util.List;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.dto.UpdateListingDTO;
import com.schemax.foodforward.model.Item;
import com.schemax.foodforward.model.Listing;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;

@Service
public interface ListingService {
	 List<ListingDTO> searchListings(ListingSearchDTO searchDTO);
	 ResponseEntity<String> addListing(CreateListingDTO listing);
	ResponseEntity<Listing> getListingDetails(Long listingId);
	ResponseEntity<List<Listing>> getAllListings(Long donorId);
	ResponseEntity<String> updateListing(UpdateListingDTO updateListingDTO);
	ResponseEntity<List<Item>> getItems(String searchQuery);
	ResponseEntity<String> saveItem(Item item);
}