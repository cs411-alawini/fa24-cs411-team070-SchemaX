package com.schemax.foodforward.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.schemax.foodforward.model.Listing;
import com.schemax.foodforward.model.User;
import com.schemax.foodforward.repository.ListingRepository;
import com.schemax.foodforward.repository.UserRepository;
import com.schemax.foodforward.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";

	@Autowired
	private ListingRepository listingRepository;

	@Autowired
	private UserRepository userRepository;

	private final String apiKey = "AIzaSyC5v1erJPoEpLYQVgwuzoeBOOi5ZCH6c_Q";

	public void updateListingsWithCoordinates() {
		List<Listing> listingsWithoutCoordinates = listingRepository.findAllByLatitudeIsNullAndLongitudeIsNull();

		RestTemplate restTemplate = new RestTemplate();

		for (Listing listing : listingsWithoutCoordinates) {
			try {
				String formattedLocation = listing.getLocation().replace(" ", "+");
				String url = String.format(GEOCODING_API_URL, formattedLocation, apiKey);

				String response = restTemplate.getForObject(url, String.class);
				JSONObject jsonResponse = new JSONObject(response);

				System.out.println(jsonResponse);

				if ("OK".equals(jsonResponse.getString("status"))) {
					JSONObject locationData = jsonResponse.getJSONArray("results").getJSONObject(0)
							.getJSONObject("geometry").getJSONObject("location");

					double latitude = locationData.getDouble("lat");
					double longitude = locationData.getDouble("lng");

					listing.setLatitude(latitude);
					listing.setLongitude(longitude);
					listingRepository.save(listing);

				} else {
					System.out.println("Failed to get coordinates for address: " + listing.getLocation());
				}
			} catch (Exception e) {
				System.out.println("Error processing address: " + listing.getLocation() + ". Error: " + e.getMessage());
			}
		}
	}

	@Override
	public void updateUsersWithCoordinates() {
		List<User> usersWithoutCoordinates = userRepository.findAllByLatitudeIsNullAndLongitudeIsNull();

		RestTemplate restTemplate = new RestTemplate();

		for (User user : usersWithoutCoordinates) {
			try {
				String formattedLocation = user.getLocation().replace(" ", "+");
				String url = String.format(GEOCODING_API_URL, formattedLocation, apiKey);

				String response = restTemplate.getForObject(url, String.class);
				JSONObject jsonResponse = new JSONObject(response);

				System.out.println(jsonResponse);

				if ("OK".equals(jsonResponse.getString("status"))) {
					JSONObject locationData = jsonResponse.getJSONArray("results").getJSONObject(0)
							.getJSONObject("geometry").getJSONObject("location");

					double latitude = locationData.getDouble("lat");
					double longitude = locationData.getDouble("lng");

					user.setLatitude(latitude);
					user.setLongitude(longitude);
					userRepository.save(user);

				} else {
					System.out.println("Failed to get coordinates for address: " + user.getLocation());
				}
			} catch (Exception e) {
				System.out.println("Error processing address: " + user.getLocation() + ". Error: " + e.getMessage());
			}
		}
	}
}
