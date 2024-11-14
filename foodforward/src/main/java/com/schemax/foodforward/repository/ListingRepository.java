package com.schemax.foodforward.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("SELECT l FROM Listing l ORDER BY l.listingId ASC LIMIT 10")
    List<Listing> findTop10Listings();
}