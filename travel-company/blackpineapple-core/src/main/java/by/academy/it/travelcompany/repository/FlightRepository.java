package by.academy.it.travelcompany.repository;

import by.academy.it.travelcompany.entity.Flight;
import by.academy.it.travelcompany.entity.RouteMap;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface FlightRepository extends CrudRepository<Flight, Serializable> {

    Flight getByRouteMapAndDepartureDateTimeAndFlightNumber(RouteMap routeMap, LocalDateTime departureDateTime,String flightNumber);

}
