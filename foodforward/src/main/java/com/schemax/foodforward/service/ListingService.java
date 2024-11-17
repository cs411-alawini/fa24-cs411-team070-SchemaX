package com.schemax.foodforward.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;

@Service
public interface ListingService {
	public List<ListingDTO> searchListings(ListingSearchDTO searchDTO);
}