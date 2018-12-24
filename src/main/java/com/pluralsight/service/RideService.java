package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	Ride createRide(Ride Ride);
	
	List<Ride> getRides();
	
	public Ride getRide(Integer id);

	Ride updateRide(Ride ride);

	void batch();

	void deleteRide(Integer id);

}