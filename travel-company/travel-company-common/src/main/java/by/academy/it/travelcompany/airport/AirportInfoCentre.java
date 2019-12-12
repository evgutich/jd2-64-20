package by.academy.it.travelcompany.airport;

/**
 * This class contains static method that help to plan trip,
 * find destination airport, find airport on same city or same area,
 * to prepare information to make correct request to Wizzair or Ryanair web site
 * */

import java.util.*;

public class AirportInfoCentre {
private static Set<Airport> allStartAirports = new TreeSet<>();
private static Set<Airport> allAirports = new TreeSet<>();
private static Set<Airport> allAirportsFromStart = new TreeSet<>();
private static Set<Airport> allAirportsFromVNO = new TreeSet<>();
private static Set<Airport> allAirportsFromVNORY = new TreeSet<>();
private static Set<Airport> allAirportsFromVNOWIZZ = new TreeSet<>();
private static Set<Airport> allAirportsFromKUN = new TreeSet<>();
private static Set<Airport> allAirportsFromKUNRY = new TreeSet<>();
private static Set<Airport> allAirportsFromKUNWIZZ = new TreeSet<>();
private static Set<Airport> allAirportsFromWAW = new TreeSet<>();
private static Set<Airport> allAirportsFromWAWWIZZ = new TreeSet<>();
private static Set<Airport> allAirportsFromWMI = new TreeSet<>();
private static Set<Airport> allAirportsFromWMIRY = new TreeSet<>();

    static {
        // allStartAirports initialisation
        for (int i = 0; i < AirportStartPoint.values().length; i++){
            allStartAirports.add(new Airport(AirportStartPoint.values()[i].toString(), AirportStartPoint.values()[i].getCountry(), AirportStartPoint.values()[i].getCity()));
        }
        // allAirportsFromVNORY initialisation
        for (int i = 0; i < AirportFromVNORY.values().length; i++){
            allAirportsFromVNORY.add(new Airport(AirportFromVNORY.values()[i].toString(),AirportFromVNORY.values()[i].getCountry(),AirportFromVNORY.values()[i].getCity()));
        }
        // allAirportsFromVNOWIZZ initialisation
        for (int i = 0; i < AirportFromVNOWIZZ.values().length; i++){
            allAirportsFromVNOWIZZ.add(new Airport(AirportFromVNOWIZZ.values()[i].toString(),AirportFromVNOWIZZ.values()[i].getCountry(),AirportFromVNOWIZZ.values()[i].getCity()));
        }
        // allAirportsFromVNO initialisation
        allAirportsFromVNO.addAll(allAirportsFromVNORY);
        allAirportsFromVNO.addAll(allAirportsFromVNOWIZZ);
        // allAirportsFromKUNRY initialisation
        for (int i = 0; i < AirportFromKUNRY.values().length; i++){
            allAirportsFromKUNRY.add(new Airport(AirportFromKUNRY.values()[i].toString(),AirportFromKUNRY.values()[i].getCountry(),AirportFromKUNRY.values()[i].getCity()));
        }
        // allAirportsFromKUNWIZZ initialisation
        for (int i = 0; i < AirportFromKUNWIZZ.values().length; i++){
            allAirportsFromKUNWIZZ.add(new Airport(AirportFromKUNWIZZ.values()[i].toString(),AirportFromKUNWIZZ.values()[i].getCountry(),AirportFromKUNWIZZ.values()[i].getCity()));
        }
        // allAirportsFromKUN initialisation
        allAirportsFromKUN.addAll(allAirportsFromKUNRY);
        allAirportsFromKUN.addAll(allAirportsFromKUNWIZZ);
        // allAirportsFromWAWWIZZ initialisation
        for (int i = 0; i < AirportFromWAWWIZZ.values().length; i++){
            allAirportsFromWAWWIZZ.add(new Airport(AirportFromWAWWIZZ.values()[i].toString(),AirportFromWAWWIZZ.values()[i].getCountry(),AirportFromWAWWIZZ.values()[i].getCity()));
        }
        // allAirportsFromWAW initialisation
        allAirportsFromWAW.addAll(allAirportsFromWAWWIZZ);
        // allAirportsFromWMIRY initialisation
        for (int i = 0; i < AirportFromWMIRY.values().length; i++){
            allAirportsFromWMIRY.add(new Airport(AirportFromWMIRY.values()[i].toString(),AirportFromWMIRY.values()[i].getCountry(),AirportFromWMIRY.values()[i].getCity()));
        }
        // allAirportsFromWMI initialisation
        allAirportsFromWMI.addAll(allAirportsFromWMIRY);
        // allAirportsFromStart initialisation
        allAirportsFromStart.addAll(allAirportsFromWMI);
        allAirportsFromStart.addAll(allAirportsFromWAW);
        allAirportsFromStart.addAll(allAirportsFromVNO);
        allAirportsFromStart.addAll(allAirportsFromKUN);
        // allAirports initialisation
        allAirports.addAll(allAirportsFromStart);
        allAirports.addAll(allStartAirports);
    }

    /**
     * Get all start airport
     * @return set of all start airport
     */

    public static Set<Airport> getAllStartAirports() {
        return allStartAirports;
    }

    /**
     * Get all airport
     * @return set of all airport
     */

    public static Set<Airport> getAllAirports() {
        return allAirports;
    }

    /**
     * Get all airport that you can get to from all starting airport
     * @return set of all airport that you can get to from all starting airport
     */

    public static Set<Airport> getAllAirportsFromStart() {
        return allAirportsFromStart;
    }

    /**
     * Get all airport that you can get to from VNO (one of starting airports)
     * @return set of all airport that you can get to from VNO (one of starting airports)
     */

    public static Set<Airport> getAllAirportsFromVNO() {
        return allAirportsFromVNO;
    }

    /**
     * Get all airport that you can get to from VNO (one of starting airports) by Ryanair company
     * @return set of all airport that you can get to from VNO (one of starting airports) by Ryanair company
     */

    public static Set<Airport> getAllAirportsFromVNORY() {
        return allAirportsFromVNORY;
    }

    /**
     * Get all airport that you can get to from VNO (one of starting airports) by Wizzair company
     * @return set of all airport that you can get to from VNO (one of starting airports) by Wizzair company
     */

    public static Set<Airport> getAllAirportsFromVNOWIZZ() {
        return allAirportsFromVNOWIZZ;
    }

    /**
     * Get all airport that you can get to from KUN (one of starting airports)
     * @return set of all airport that you can get to from KUN (one of starting airports)
     */

    public static Set<Airport> getAllAirportsFromKUN() {
        return allAirportsFromKUN;
    }

    /**
     * Get all airport that you can get to from KUN (one of starting airports) by Ryanair company
     * @return set of all airport that you can get to from KUN (one of starting airports) by Ryanair company
     */

    public static Set<Airport> getAllAirportsFromKUNRY() {
        return allAirportsFromKUNRY;
    }

    /**
     * Get all airport that you can get to from KUN (one of starting airports) by Wizzair company
     * @return set of all airport that you can get to from KUN (one of starting airports) by Wizzair company
     */

    public static Set<Airport> getAllAirportsFromKUNWIZZ() {
        return allAirportsFromKUNWIZZ;
    }

    /**
     * Get all airport that you can get to from WAW (one of starting airports)
     * @return set of all airport that you can get to from WAW (one of starting airports)
     */

    public static Set<Airport> getAllAirportsFromWAW() {
        return allAirportsFromWAW;
    }

    /**
     * Get all airport that you can get to from WAW (one of starting airports) by Wizzair company
     * @return set of all airport that you can get to from WAW (one of starting airports) by Wizzair company
     */

    public static Set<Airport> getAllAirportsFromWAWWIZZ() {
        return allAirportsFromWAWWIZZ;
    }

    /**
     * Get all airport that you can get to from WMI (one of starting airports)
     * @return set of all airport that you can get to from WMI (one of starting airports)
     */

    public static Set<Airport> getAllAirportsFromWMI() {
        return allAirportsFromWMI;
    }

    /**
     * Get all airport that you can get to from WMI (one of starting airports) by Ryanair company
     * @return set of all airport that you can get to from WMI (one of starting airports) by Ryanair company
     */

    public static Set<Airport> getAllAirportsFromWMIRY() {
        return allAirportsFromWMIRY;
    }

    /**
     * Get all airport that you can get from @param airport
     * * @return set of all airport that you can get from @param airport
     */

    public static Set <Airport> getAllDestinations(Airport airport){
        if (airport.equals(new Airport("VNO"))){
            return allAirportsFromVNO;
        }else if (airport.equals(new Airport("KUN"))){
            return allAirportsFromKUN;
        }else if (airport.equals(new Airport("WAW"))){
            return allAirportsFromWAW;
        }else if (airport.equals(new Airport("WMI"))){
            return allAirportsFromWMI;
        }else {
            Set <Airport> airportsDest = new TreeSet<>();
            if (getAllAirportsFromVNO().contains(airport)){
                airportsDest.add(new Airport("VNO"));
            }
            if (getAllAirportsFromKUN().contains(airport)){
                airportsDest.add(new Airport("KUN"));
            }
            if (getAllAirportsFromWAW().contains(airport)){
                airportsDest.add(new Airport("WAW"));
            }
            if (getAllAirportsFromVNO().contains(airport)){
                airportsDest.add(new Airport("WMI"));
            }
            return airportsDest;
        }
    }

    /**
     * Get all airports that you can get to and companies from @param airport
     * @return HashMap, where key is Company enum (Company.java) and value is
     * Treeset of Airport
     */

    public static Map<Airline,Set<Airport>> getAllDestinationsAndCompany(Airport airport){
        Map <Airline,Set<Airport>> allDestinationsAndCompany = new HashMap<>();
        if (airport.equals(new Airport("VNO"))){
            allDestinationsAndCompany.put(Airline.RY,allAirportsFromVNORY);
            allDestinationsAndCompany.put(Airline.WIZZ,allAirportsFromVNOWIZZ);
        } else if(airport.equals(new Airport("KUN"))){
            allDestinationsAndCompany.put(Airline.RY,allAirportsFromKUNRY);
            allDestinationsAndCompany.put(Airline.WIZZ,allAirportsFromKUNWIZZ);
        } else if(airport.equals(new Airport("WMI"))){
            allDestinationsAndCompany.put(Airline.RY,allAirportsFromWMIRY);
        } else if(airport.equals(new Airport("WAW"))){
            allDestinationsAndCompany.put(Airline.WIZZ,allAirportsFromWAWWIZZ);
        } else {

            if (allAirportsFromVNORY.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.RY)){
                    Set <Airport> airports1 = new TreeSet<>();
                    airports1.add(new Airport("VNO"));
                    allDestinationsAndCompany.put(Airline.RY,airports1);
                } else {
                    allDestinationsAndCompany.get(Airline.RY).add(new Airport("VNO"));
                }
            }

            if (allAirportsFromVNOWIZZ.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.WIZZ)){
                    Set <Airport> airports2 = new TreeSet<>();
                    airports2.add(new Airport("VNO"));
                    allDestinationsAndCompany.put(Airline.WIZZ,airports2);
                } else {
                    allDestinationsAndCompany.get(Airline.WIZZ).add(new Airport("VNO"));
                }
            }

            if (allAirportsFromKUNRY.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.RY)){
                    Set <Airport> airports3 = new TreeSet<>();
                    airports3.add(new Airport("KUN"));
                    allDestinationsAndCompany.put(Airline.RY,airports3);
                } else {
                    allDestinationsAndCompany.get(Airline.RY).add(new Airport("KUN"));
                }

            }

            if (allAirportsFromKUNWIZZ.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.WIZZ)){
                    Set <Airport> airports4 = new TreeSet<>();
                    airports4.add(new Airport("KUN"));
                    allDestinationsAndCompany.put(Airline.WIZZ,airports4);
                } else {
                    allDestinationsAndCompany.get(Airline.WIZZ).add(new Airport("KUN"));
                }
            }

            if (allAirportsFromWAWWIZZ.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.WIZZ)){
                    Set <Airport> airports5 = new TreeSet<>();
                    airports5.add(new Airport("WAW"));
                    allDestinationsAndCompany.put(Airline.WIZZ,airports5);
                } else {
                    allDestinationsAndCompany.get(Airline.WIZZ).add(new Airport("WAW"));
                }
            }

            if (allAirportsFromWMIRY.contains(airport)){
                if (!allDestinationsAndCompany.containsKey(Airline.RY)){
                    Set <Airport> airports6 = new TreeSet<>();
                    airports6.add(new Airport("WMI"));
                    allDestinationsAndCompany.put(Airline.RY,airports6);
                } else {
                    allDestinationsAndCompany.get(Airline.RY).add(new Airport("WMI"));
                }
            }

        }
        return allDestinationsAndCompany;
    }

}
