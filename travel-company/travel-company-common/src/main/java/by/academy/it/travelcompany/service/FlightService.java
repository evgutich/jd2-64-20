package by.academy.it.travelcompany.service;

import by.academy.it.travelcompany.airport.Airport;
import by.academy.it.travelcompany.flight.Flight;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Flight service
 */

public interface FlightService {

    /**
     * Get all flights
     * @return list of found flights
     */

    List <Flight> getAllFlights();

    /**
     * Add new flight
     * @param flight flight to save
     * @return new flight with generated Id
     */

    Flight addFlight(Flight flight);

    /**
     * Delete flight by id
     * flight with @param id delete from list
     */

    void deleteFlightById(Long id);

    /**
     * update object in list with same id of
     * @param flight flight to update (by id)
     * @return flight updated flight
     */

    Flight updateFlightById(Flight flight);

    /**
     * update object in list with same LocalDate, Airline, flight number, Airport origin, Airport destination (Flight.equals())
     * @param flight flight to update
     * @return flight updated flight
     * If flight  doesn't exist on list
     * It will be created
     * See Flight class equals method for more information
     * If Flight or important field is null, trows IOException;
     */

    Flight updateOrCreate(Flight flight) throws IOException;


}
