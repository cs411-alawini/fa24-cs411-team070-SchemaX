package com.schemax.foodforward.repository;

import com.schemax.foodforward.model.Donor;
import org.springframework.data.repository.CrudRepository;

public interface DonorRepository  extends CrudRepository<Donor, Long> {
    //Crete Donor
    //Get DonorDetails
}
