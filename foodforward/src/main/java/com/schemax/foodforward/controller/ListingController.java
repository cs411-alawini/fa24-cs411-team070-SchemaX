package com.schemax.foodforward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.service.ListingService;

@RestController
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping("/top10")
    public List<Listing> getTop10Listings() {
        return listingService.getTop10Listings();
    }
}