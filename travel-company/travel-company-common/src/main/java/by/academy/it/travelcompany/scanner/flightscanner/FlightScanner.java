package by.academy.it.travelcompany.scanner.flightscanner;

import by.academy.it.travelcompany.airport.Airline;
import by.academy.it.travelcompany.airport.Airport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FlightScanner{

    /**
     * This is scanner flights on ryanair.com and wizzair.com
     * @param airline airport.Airline RY or WIZZ
     * @param localDate starting date for parsing
     * @param dayQuantityForSearchFromToday Integer how deep to scan day
     * @param origin Airport origin
     * @param destination Airport destination
     * new flight create by FlightServiceImpl
     *
     */

    void parseFlights (Airline airline, LocalDate localDate, Integer dayQuantityForSearchFromToday, Airport origin, Airport destination ) throws IOException;

}
