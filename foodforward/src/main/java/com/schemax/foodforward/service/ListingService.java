package com.schemax.foodforward.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schemax.foodforward.model.Listing;

@Service
public interface ListingService {
    List<Listing> getTop10Listings();
}