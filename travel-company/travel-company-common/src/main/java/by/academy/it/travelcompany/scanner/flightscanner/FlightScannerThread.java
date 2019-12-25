package by.academy.it.travelcompany.scanner.flightscanner;

import by.academy.it.travelcompany.airport.Airline;
import by.academy.it.travelcompany.airport.Airport;

import java.time.LocalDate;

public class FlightScannerThread extends Thread {

    private String direction;
    private Airline airline;
    private Airport originAirport;
    private Airport destinationAirport;
    private LocalDate startingDate;
    private Integer dayQuantityForSearch;


    public FlightScannerThread(Airline airline, Airport originAirport, Airport destinationAirport, LocalDate startingDate, Integer dayQuantityForSearch, String direction) {
        this.airline = airline;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.startingDate = startingDate;
        this.dayQuantityForSearch = dayQuantityForSearch;
        this.direction = direction;
    }


    @Override
    public void run() {
        FlightScanner flightScanner = new FlightScannerImpl();
        if (airline.equals(Airline.RY)) {
            flightScanner.parseFlightsRY(startingDate, dayQuantityForSearch, originAirport, destinationAirport, direction);
        }
        if (airline.equals(Airline.WIZZ)) {
            flightScanner.parseFlightsWIZZ(startingDate, dayQuantityForSearch, originAirport, destinationAirport, direction);
        }
    }


}
