package com.schemax.foodforward.service;

import java.util.List;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.model.Listing;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;

@Service
public interface ListingService {
	public List<ListingDTO> searchListings(ListingSearchDTO searchDTO);
	public ResponseEntity<String> addListing(CreateListingDTO listing);
}