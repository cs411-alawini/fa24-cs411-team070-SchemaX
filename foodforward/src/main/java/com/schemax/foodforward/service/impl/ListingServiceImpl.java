package com.schemax.foodforward.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.CreateListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;
import com.schemax.foodforward.dto.UpdateListingDTO;
import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.model.Item;
import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.repository.DonorRepository;
import com.schemax.foodforward.repository.ItemRepository;
import com.schemax.foodforward.repository.ListingRepository;
import com.schemax.foodforward.service.ListingService;

@Slf4j
@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Listing> searchListings(ListingSearchDTO searchDTO) {
        List<Listing> listings = listingRepository.findListingsWithFilters(
                searchDTO.getFoodType(),
                searchDTO.getQuantityNeeded(),
                searchDTO.getExpiryDate(),
                searchDTO.getPickupTimeStart(),
                searchDTO.getPickupTimeEnd(),
                searchDTO.getLocation(),
                searchDTO.getDistance(),
                searchDTO.getUserLongitude(),
                searchDTO.getUserLatitude(),
            searchDTO.getRecipientId());
        System.out.println("LISTINGS");
        System.out.println(listings);
        return listings;
    }

    @Override
    public ResponseEntity<String> addListing(CreateListingDTO listing) {
        try {
            Long listingId = listingRepository.saveListing(listing);
            return ResponseEntity.ok("Listing created successfully with ID: " + listingId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating listing: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Listing> getListingDetails(Long listingId) {
        Listing listing = listingRepository.findListingByListingId(listingId);
        if (listing != null) {
            return ResponseEntity.ok(listing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<Listing>> getAllListings(Long donorId) {
        List<Listing> listings = listingRepository.findAllListingsByDonor(donorId);
        if (listings != null) {
            return ResponseEntity.ok(listings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateListing(UpdateListingDTO updateListingDTO) {
        try {
            int listingId = listingRepository.updateListing(updateListingDTO);
            return ResponseEntity.ok("Listing updated successfully with ID: " + listingId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating listing: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Item>> getItems(String searchQuery) {
        List<Item> items = listingRepository.getItems(searchQuery);
        if (items != null) {
            return ResponseEntity.ok(items);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> saveItem(Item item) {
        try {
            Long itemId = listingRepository.saveItem(item);
            return ResponseEntity.ok("Item added successfully with ID: " + itemId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding item: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteListing(Long listingId) {
        try {
             listingRepository.deleteListing(listingId);
            return ResponseEntity.ok("Listing deleted successfully with ID: " + listingId);
        } catch (Exception e) {
            log.error("Exception while deleting listing : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting listing: " + e.getMessage());
        }    }
}
