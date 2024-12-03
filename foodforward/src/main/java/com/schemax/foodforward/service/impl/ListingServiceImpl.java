package com.schemax.foodforward.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.dto.CreateListingItemDTO;
import com.schemax.foodforward.model.Donor;
import com.schemax.foodforward.model.Item;
import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.model.ListingItem;
import com.schemax.foodforward.repository.DonorRepository;
import com.schemax.foodforward.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;
import com.schemax.foodforward.repository.ListingRepository;
import com.schemax.foodforward.service.ListingService;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ListingDTO> searchListings(ListingSearchDTO searchDTO) {
    	System.out.println(searchDTO);
        List<Object[]> results = listingRepository.findListingsWithFilters(
                searchDTO.getFoodType(),
                searchDTO.getQuantityNeeded(),
                searchDTO.getExpiryDate(),
                searchDTO.getPickupTimeStart(),
                searchDTO.getPickupTimeEnd(),
                searchDTO.getLocation(),
                searchDTO.getDistance(),
                searchDTO.getUserLongitude(),
                searchDTO.getUserLatitude());

        List<ListingDTO> listings = new ArrayList<>();

        for (Object[] row : results) {
            ListingDTO dto = new ListingDTO();
            dto.setListingId((Long) row[0]);
            dto.setLocation((String) row[1]);
            dto.setItemId((Long) row[2]);
            dto.setItemName((String) row[3]);
            dto.setCategory((String) row[4]);
            dto.setQuantity((Long) row[5]);
            dto.setExpirationDate((Date) row[6]);
            dto.setDonorId((Long) row[7]);
            dto.setDonorName((String) row[8]);

            listings.add(dto);
        }

        return listings;
    }

    @Override
    public ResponseEntity<String> addListing(CreateListingDTO createListingDTO) {
        Listing listing = new Listing();
        List<ListingItem> listingItems = new ArrayList<>();
        Donor donor = donorRepository.findById(createListingDTO.getDonorId()).orElseThrow(() -> new RuntimeException("Donor not found"));

        for(CreateListingItemDTO listingItemDTO : createListingDTO.getListingItems()) {
            Item item = itemRepository.findById(listingItemDTO.getItemId()).orElseThrow(() -> new RuntimeException("Item not found"));
            ListingItem listingItem = new ListingItem();
            listingItem.setQuantity(listingItemDTO.getQuantity());
            listingItem.setExpirationDate(listingItemDTO.getExpirationDate());
            listingItem.setItem(item);
            listingItem.setStatus("AVAILABLE");
            listingItem.setListing(listing);
            listingItems.add(listingItem);
        }
        listing.setDonor(donor);
        listing.setLatitude(createListingDTO.getLatitude());
        listing.setLongitude(createListingDTO.getLongitude());
        listing.setPickupTimeRange(createListingDTO.getPickupTimeRange());
        listing.setType(createListingDTO.getType());
        listing.setStatus("AVAILABLE");
        listing.setListingItems(listingItems);
        listingRepository.save(listing);
        return new ResponseEntity<>("Created listing with id : " + listing.getListingId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Listing> getListingDetails(Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Listing not found"));
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }
}