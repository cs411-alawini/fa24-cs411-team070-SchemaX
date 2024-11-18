package com.schemax.foodforward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}