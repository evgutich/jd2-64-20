package by.academy.it.travelcompany.scanner.flightscanner;

import by.academy.it.travelcompany.dao.FlightDAO;
import by.academy.it.travelcompany.dao.FlightDAOImpl;
import by.academy.it.travelcompany.service.global.ScheduleService;
import by.academy.it.travelcompany.service.global.ScheduleServiceImpl;
import by.academy.it.travelcompany.travelitem.airport.Airline;
import by.academy.it.travelcompany.travelitem.airport.Airport;
import by.academy.it.travelcompany.travelitem.flight.Flight;
import by.academy.it.travelcompany.travelitem.routemap.RouteMap;
import by.academy.it.travelcompany.travelitem.schedule.Schedule;
import by.academy.it.travelcompany.service.local.FlightServiceLocal;
import by.academy.it.travelcompany.service.local.FlightServiceLocalImpl;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.*;

public class FlightScannerImpl extends Thread {

    private static final FlightDAO FLIGHT_DAO = FlightDAOImpl.getInstance();

    private static final int DELAY_REQ_RY = 1000;
    private static final int DELAY_REQ_RY_SYNC = 1500;
    private static final int DELAY_REQ_WIZZ = 100;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightScannerImpl.class);
    private static final FlightServiceLocal FLIGHT_SERVICE = FlightServiceLocalImpl.getInstance();
    private static final ScheduleService SCHEDULE_SERVICE = ScheduleServiceImpl.getInstance();

    private static final Object SYNC_RY = new Object();

    private Long searchId;
    private Airline airline;
    private Airport originAirport;
    private Airport destinationAirport;
    private LocalDate startingDate;
    private Integer dayQuantity;
    private String direction;

    public FlightScannerImpl(Long searchId, Airline airline, Airport originAirport, Airport destinationAirport, LocalDate startingDate, Integer dayQuantity, String direction) {
        this.searchId = searchId;
        this.airline = airline;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.startingDate = startingDate;
        this.dayQuantity = dayQuantity;
        this.direction = direction;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Integer getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(Integer dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Flight> parseFlightsRY() {
        LOGGER.info("Start parsing on Ryanair.com, Starting date:" + startingDate + " dayQuantity: " + dayQuantity
                + " originAirport: " + originAirport + " destinationAirport: " + destinationAirport + " direction: " + direction + " searchId: " + searchId);
        final LocalDate finishLocalDate = startingDate.plusDays(dayQuantity);
        RouteMap routeMap = new RouteMap(airline.toString(),originAirport.getCode(),destinationAirport.getCode(),direction);
        Schedule schedule = SCHEDULE_SERVICE.getSchedule(routeMap);
        Set <LocalDate> dateSet = schedule.getScheduleSet();
        for (LocalDate l : dateSet
             ) {
            System.out.println(l);
        }
        LocalDate currentLocalDate = LocalDate.of(startingDate.getYear(), startingDate.getMonthValue(), startingDate.getDayOfMonth());

        List<Flight> result = new ArrayList<>();
        while (currentLocalDate.isBefore(finishLocalDate.plusDays(1))) {
            try {
                Thread.sleep(DELAY_REQ_RY);
                System.out.println("CLD - "+currentLocalDate);

                String req = getReqStringRY(currentLocalDate);
                JSONObject json = null;
                synchronized (SYNC_RY) {
                    json = new JSONObject(readUrl(req));
                    Thread.sleep(DELAY_REQ_RY_SYNC);
                }
                JSONArray jsonTrips = json.getJSONArray("trips");
                String currency = (String) json.get("currency");
                JSONObject jsonTrip = (JSONObject) jsonTrips.get(0);
                JSONArray jsonDates = jsonTrip.getJSONArray("dates");
                JSONObject jsonDate = (JSONObject) jsonDates.get(0);
                JSONArray jsonFlights = jsonDate.getJSONArray("flights");

                for (int i = 0; i < jsonFlights.length(); i++) {
                    JSONObject jsonFlight = (JSONObject) jsonFlights.get(i);
                    JSONArray time = jsonFlight.getJSONArray("time");
                    String departureDateTime = (String) time.get(0);
                    String arriveDateTime = (String) time.get(1);
                    String flightNumber = (String) jsonFlight.get("flightNumber");
                    JSONObject jsonRegularFare = jsonFlight.getJSONObject("regularFare");
                    JSONArray jsonFares = jsonRegularFare.getJSONArray("fares");
                    JSONObject jsonFare = (JSONObject) jsonFares.get(0);
                    Double amount = (Double) (jsonFare.get("amount"));

                    LocalDateTime arriveLocalDateTime = getLocalDateTimeFromString(arriveDateTime,"T","-",":");
                    LocalDateTime departureLocalDateTime = getLocalDateTimeFromString(departureDateTime,"T","-",":");

                    Flight f = new Flight(null, originAirport, destinationAirport, departureLocalDateTime, arriveLocalDateTime, Airline.RY, currency, amount, flightNumber);
                    f.setDirection(direction);
                    f.setSearchId(searchId);
                    FLIGHT_SERVICE.updateOrCreate(f);
                    result.add(f);
                    LOGGER.info("Flight found: " + f);
                    try {
                        Long id = FLIGHT_DAO.create(f);
                        LOGGER.info ("Flight successful added to base, id: " + id);
                    }catch (SQLException e){
                        LOGGER.error("Error during adding flight to base",e);
                    }
                }
            } catch (Exception e) {
                System.out.println("HIFROMCATCHBLOCK!");
            } finally {
                currentLocalDate = schedule.getNextDate(currentLocalDate);
            }
        }
        LOGGER.info("Flight scanning successfully ended");
        return result;
    }

    public List<Flight> parseFlightsWIZZ() {
        LOGGER.info("Start parsing on Wizzair.com, Starting date:" + startingDate + " dayQuantity: " + dayQuantity
                + " originAirport: " + originAirport + " destinationAirport: " + destinationAirport + " direction: " + direction + " searchId: " + searchId);

        final LocalDate finishLocalDate = startingDate.plusDays(dayQuantity);
        Schedule schedule = SCHEDULE_SERVICE.getSchedule(Airline.WIZZ, originAirport, destinationAirport, startingDate, dayQuantity);
        List<Flight> result = new ArrayList<>();
        Map<String, List<String>> authMap = getWizzAirCookiesAndTokens();

        LocalDate currentLocalDate = LocalDate.of(startingDate.getYear(), startingDate.getMonthValue(), startingDate.getDayOfMonth());
        while (currentLocalDate.isBefore(finishLocalDate.plusDays(1))) {

            try {
                Thread.sleep(DELAY_REQ_WIZZ);
            } catch (InterruptedException e) {
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://be.wizzair.com/10.3.0/Api/search/search");

            httpPost.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpPost.setHeader("accept-encoding", "gzip, deflate, br");
            httpPost.setHeader("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            httpPost.setHeader("cache-control", "no-cache");
            httpPost.setHeader("pragma", "no-cache");
            httpPost.setHeader("sec-fetch-mode", "navigate");
            httpPost.setHeader("sec-fetch-site", "none");
            httpPost.setHeader("sec-fetch-user", "?1");
            httpPost.setHeader("upgrade-insecure-requests", "1");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            httpPost.setHeader("x-requestverificationtoken", authMap.get("x-requestverificationtoken").get(0));

            for (int j = 0; j < authMap.get("Set-Cookie").size(); j++) {
                httpPost.addHeader("cookie", authMap.get("Set-Cookie").get(j));
            }

            HttpEntity httpEntity;

            String stringEntity = "{\"isFlightChange\":false,\"isSeniorOrStudent\":false,\"flightList\":[{\"departureStation\":\"" +
                    originAirport.getCode() +
                    "\",\"arrivalStation\":\"" +
                    destinationAirport.getCode() +
                    "\",\"departureDate\":\"" +
                    getDateStringWIZZ(currentLocalDate) +
                    "\"}],\"adultCount\":1,\"childCount\":0,\"infantCount\":0,\"wdc\":true}";

            try {
                httpEntity = new StringEntity(stringEntity);
                httpPost.setEntity(httpEntity);
            } catch (UnsupportedEncodingException e) {
            }

            try (
                    CloseableHttpResponse response = httpClient.execute(httpPost)
            ) {

                HttpEntity responseEntity = response.getEntity();
                String responseBodyString = convertInputStreamToString(responseEntity.getContent());

                JSONObject json = new JSONObject(responseBodyString);

                JSONArray jsonOutBoundFlights = json.getJSONArray("outboundFlights");

                for (int l = 0; l < jsonOutBoundFlights.length(); l++) {
                    JSONObject jsonOutBoundFlight = (JSONObject) jsonOutBoundFlights.get(l);
                    String flightNumber = jsonOutBoundFlight.getString("carrierCode") + " " + jsonOutBoundFlight.getString("flightNumber");

                    String arriveDateTime = jsonOutBoundFlight.getString("arrivalDateTime");
                    String departureDateTime = jsonOutBoundFlight.getString("departureDateTime");

                    JSONArray jsonFares = jsonOutBoundFlight.getJSONArray("fares");
                    JSONObject jsonFare = (JSONObject) jsonFares.get(3);

                    JSONObject jsonBasePrice = jsonFare.getJSONObject("basePrice");
                    Double amount = (Double) jsonBasePrice.get("amount");
                    String currencyCode = (String) jsonBasePrice.get("currencyCode");

                    LocalDateTime departureLocalDateTime = getLocalDateTimeFromString(departureDateTime,"T","-",":");
                    LocalDateTime arriveLocalDateTime = getLocalDateTimeFromString(arriveDateTime,"T","-",":");

                    Flight f = new Flight(null, originAirport, destinationAirport, departureLocalDateTime, arriveLocalDateTime, Airline.WIZZ, currencyCode, amount, flightNumber);
                    f.setDirection(direction);
                    f.setSearchId(searchId);
                    FLIGHT_SERVICE.updateOrCreate(f);
                    result.add(f);
                    LOGGER.info("Flight found: " + f);
                }

            } catch (Exception e) {
            } finally {
                currentLocalDate = schedule.getNextDate(currentLocalDate);
            }
        }
        return result;
    }

    @Override
    public void run() {
        if (airline.equals(Airline.RY)) {
            parseFlightsRY();
        }
        if (airline.equals(Airline.WIZZ)) {
            parseFlightsWIZZ();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*************************************PRIVATE METHODS****************************************************************//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, List<String>> getWizzAirCookiesAndTokens() {
        Map<String, List<String>> result = new HashMap<>();
        List<String> listOfValue = new ArrayList<>();
        List<String> keyList = new ArrayList<>();

        result.put("Set-Cookie", listOfValue);
        result.put("x-requestverificationtoken", keyList);

        final CloseableHttpClient httpClient = HttpClients.createDefault();

        final HttpUriRequest httpGet = new HttpPost("https://be.wizzair.com/10.3.0/Api/search/search");

        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
        httpGet.setHeader("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cache-control", "no-cache");
        httpGet.setHeader("pragma", "no-cache");
        httpGet.setHeader("sec-fetch-mode", "navigate");
        httpGet.setHeader("sec-fetch-site", "none");
        httpGet.setHeader("sec-fetch-user", "?1");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

        try (
                CloseableHttpResponse response1 = httpClient.execute(httpGet)
        ) {

            Header[] headersResp = response1.getAllHeaders();
            String requestVerificationToken = "";
            for (Header h : headersResp) {
                if (h.getName().equals("Set-Cookie")) {
                    listOfValue.add(h.getValue());
                    if (h.getValue().startsWith("RequestVerificationToken")) {
                        for (int k = 25; k < 57; k++) {
                            requestVerificationToken = requestVerificationToken + h.getValue().charAt(k);
                        }
                        keyList.add(requestVerificationToken);
                    }
                }
            }

        } catch (Exception e) {

        }
        return result;
    }

    private String getReqStringRY(LocalDate dateOfSearch) {
        String dateOfSearchFormatted = dateOfSearch.getYear() + "-" + dateOfSearch.getMonthValue() + "-" + dateOfSearch.getDayOfMonth();
        String req = "https://www.ryanair.com/api/booking/v4/en-gb/availability?" +
                "ADT=1&TEEN=0&CHD=0&INF=0&" +
                "DateOut=" + dateOfSearchFormatted +
                "&DateIn=&Origin=" + originAirport.getCode() +
                "&Destination=" + destinationAirport.getCode() +
                "&isConnectedFlight=false&RoundTrip=false&Discount=0&tpAdults=1&tpTeens=0&tpChildren=0&tpInfants=0&" +
                "tpStartDate=" + dateOfSearchFormatted +
                "&tpEndDate=&tpOriginIata=" + originAirport.getCode() +
                "&tpDestinationIata=" + destinationAirport.getCode() +
                "&ToUs=AGREED" +
                "&tpIsConnectedFlight=false&tpIsReturn=false&tpDiscount=0";
        return req;
    }

    private String getDateStringWIZZ(LocalDate dateOfSearch) {
        String month = dateOfSearch.getMonthValue() < 10 ? "0" + dateOfSearch.getMonthValue() : "" + dateOfSearch.getMonthValue();
        String day = dateOfSearch.getDayOfMonth() < 10 ? "0" + dateOfSearch.getDayOfMonth() : "" + dateOfSearch.getDayOfMonth();
        return dateOfSearch.getYear() + "-" + month + "-" + day;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

    private LocalDateTime getLocalDateTimeFromString(String str, String regexDateFromTime,String regexDayMonthYear, String regexMinHour){

        String date = str.split(regexDateFromTime)[0];
        String year = date.split(regexDayMonthYear)[0];
        String month = date.split(regexDayMonthYear)[1];
        String day = date.split(regexDayMonthYear)[2];
        String time = str.split(regexDateFromTime)[1];
        String hour = time.split(regexMinHour)[0];
        String min = time.split(regexMinHour)[1];
        return LocalDateTime.of(
                LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)),
                LocalTime.of(Integer.parseInt(hour), Integer.parseInt(min)));
    }
}