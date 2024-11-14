package com.schemax.foodforward.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.repository.ListingRepository;
import com.schemax.foodforward.service.ListingService;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public List<Listing> getTop10Listings() {
        return listingRepository.findTop10Listings();
    }
}
