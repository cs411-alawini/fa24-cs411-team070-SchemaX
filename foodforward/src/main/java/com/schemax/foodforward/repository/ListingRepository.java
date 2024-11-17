package com.schemax.foodforward.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schemax.foodforward.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

	@Query(value = """
			    SELECT 
			    	l.listing_id AS listingId, 
			    	l.location AS location,
			        li.item_id AS itemId, 
			        i.item_name AS itemName, 
			        i.category AS category,
			        li.quantity AS quantity, 
			        li.expiration_date AS expirationDate,
			        d.donor_id AS donorId, 
			        u.name AS donorName
			    FROM Listing l
			    JOIN ListingItem li ON l.listing_id = li.listing_id
			    JOIN Item i ON li.item_id = i.item_id
			    JOIN Donor d ON l.listed_by = d.donor_id
			    JOIN User u ON d.user_id = u.user_id
			    WHERE 
			    	(:foodType IS NULL OR i.category = :foodType)
			    	AND (:quantityNeeded IS NULL OR li.quantity >= :quantityNeeded)
			      	AND (:expiryDate IS NULL OR li.expiration_date <= :expiryDate)
			    	AND (:pickupTimeStart IS NULL OR l.pickup_time_range >= :pickupTimeStart)
			    	AND (:pickupTimeEnd IS NULL OR l.pickup_time_range <= :pickupTimeEnd)
			    	AND (:location IS NULL OR l.location LIKE '%:location%')
			    	AND (:distance IS NULL OR (
			        	:userLatitude IS NOT NULL AND
			            :userLongitude IS NOT NULL AND
			            ST_Distance_Sphere(POINT(l.longitude, l.latitude), POINT(:userLongitude, :userLatitude)) <= :distance))
			      	AND l.status = 'ACTIVE'
			      	AND li.status = 'AVAILABLE'
			      	AND (li.expiration_date IS NULL OR li.expiration_date > CURRENT_DATE)
			""", nativeQuery = true)
	List<Object[]> findListingsWithFilters(
		@Param("foodType") String foodType,
		@Param("quantityNeeded") Long quantityNeeded, 
		@Param("expiryDate") Date expiryDate,
		@Param("pickupTimeStart") String pickupTimeStart, 
		@Param("pickupTimeEnd") String pickupTimeEnd,
		@Param("location") String location, 
		@Param("distance") Double distance,
		@Param("userLongitude") Double userLongitude, 
		@Param("userLatitude") Double userLatitude
	);
	
	List<Listing> findAllByLatitudeIsNullAndLongitudeIsNull();
}