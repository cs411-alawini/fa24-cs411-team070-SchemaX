package com.schemax.foodforward.service;

import org.springframework.stereotype.Service;

@Service
public interface LocationService {
	public void updateListingsWithCoordinates();
	
	public void updateUsersWithCoordinates(); 
}
