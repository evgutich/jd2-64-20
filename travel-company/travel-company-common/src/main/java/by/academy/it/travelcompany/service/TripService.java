package by.academy.it.travelcompany.service;


import by.academy.it.travelcompany.trip.Trip;

import java.util.List;

/**
 * Trip service
 */

public interface TripService {

    /**
     * Get all trips
     * @return list of found trips
     */

    List<Trip> getAllTrips();

    /**
     * Add new trip
     * @param trip trip to save
     * @return new trip with generated id
     */

    Trip addTrip(Trip trip);

    /**
     * Delete trip by id
     * trip with @param id delete from list
     */

    void deleteTrip(Long id);

    /**
     * update object in list with same id of
     * @param trip trip to update (by id)
     * @return trip updated transfer
     */

    Trip updateTrip(Trip trip);

}
