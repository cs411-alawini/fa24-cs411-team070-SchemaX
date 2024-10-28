package com.schemax.foodforward.repository;

import com.schemax.foodforward.model.Listing;
import org.springframework.data.repository.CrudRepository;


public interface ListingRepository  extends CrudRepository<Listing, Long> {
    //
}
