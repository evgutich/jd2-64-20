package by.academy.it.travelcompany.travelitem.accommodation;

import java.time.LocalDate;
import java.util.Objects;

public class Accommodation {

    private Long id;
    private String country;
    private String city;
    private String address;
    private String nameOfAccommodation;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Accommodations accommodations;

    public Accommodation() {
    }

    public Accommodation(Long id, String country, String city, String address, String nameOfAccommodation, LocalDate checkInDate, LocalDate checkOutDate, Accommodations accommodations) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.nameOfAccommodation = nameOfAccommodation;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.accommodations = accommodations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameOfAccommodation() {
        return nameOfAccommodation;
    }

    public void setNameOfAccommodation(String nameOfAccommodation) {
        this.nameOfAccommodation = nameOfAccommodation;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Accommodations getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(Accommodations accommodations) {
        this.accommodations = accommodations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(address, that.address) &&
                Objects.equals(nameOfAccommodation, that.nameOfAccommodation) &&
                Objects.equals(checkInDate, that.checkInDate) &&
                Objects.equals(checkOutDate, that.checkOutDate) &&
                accommodations == that.accommodations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, address, nameOfAccommodation, checkInDate, checkOutDate, accommodations);
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", nameOfAccommodation='" + nameOfAccommodation + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", accommodations=" + accommodations +
                '}';
    }

}