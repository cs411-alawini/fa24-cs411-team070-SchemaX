package com.schemax.foodforward.controller;

import com.schemax.foodforward.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestService testService;


    @PostMapping("/test")
    public ResponseEntity<String> testApi(@RequestBody String req) {
        return testService.test();
    }

    /**
     * P0s
     * 1. Create Donor Profile
     * 2. Create Recipient Profile
     * 3. Add Listings
     * 4. Get Items
     * 5. Get My Listings for a Donor
     * 6. Get All Listings to search for a recipient
     * 7. Get ListingItems with a given ListingId
     * 8. Book ListingItems
     * 9. Get All Bookings by a recipients
     * 10.Create Recommendations using actual data and insert it in DB
     */

    /**
     * P1s
     * - Update Listing --> Add/Delete Item
     * - Rate and Review Donor
     */

    /**
     *  P2s
     *  - Update Profile - both donor and recipient
     */

}
