package com.schemax.foodforward.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.dto.ListingDTO;
import com.schemax.foodforward.dto.ListingSearchDTO;
import com.schemax.foodforward.repository.ListingRepository;
import com.schemax.foodforward.service.ListingService;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public List<ListingDTO> searchListings(ListingSearchDTO searchDTO) {
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
}