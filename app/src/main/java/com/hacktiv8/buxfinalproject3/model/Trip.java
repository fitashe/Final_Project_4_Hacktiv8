package com.hacktiv8.buxfinalproject3.model;

public class Trip {

    String idTrip;
    String busName;
    String price;
    Long date;
    String departCity;
    String departTerminal;
    String departHour;
    String arrivalCity;
    String arrivalTerminal;
    String arrivalHour;
    String etaJam;
    String rating;
    int seatAvailable;

    public Trip(){

    }

    public Trip(String idTrip, String busName, String price, Long date, String departCity, String departTerminal, String departHour, String arrivalCity, String arrivalTerminal, String arrivalHour, String etaJam, String rating, int seatAvailable) {
        this.idTrip = idTrip;
        this.busName = busName;
        this.price = price;
        this.date = date;
        this.departCity = departCity;
        this.departTerminal = departTerminal;
        this.departHour = departHour;
        this.arrivalCity = arrivalCity;
        this.arrivalTerminal = arrivalTerminal;
        this.arrivalHour = arrivalHour;
        this.etaJam = etaJam;
        this.rating = rating;
        this.seatAvailable = seatAvailable;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public String getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(String idTrip) {
        this.idTrip = idTrip;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDepartTerminal() {
        return departTerminal;
    }

    public void setDepartTerminal(String departTerminal) {
        this.departTerminal = departTerminal;
    }

    public String getDepartHour() {
        return departHour;
    }

    public void setDepartHour(String departHour) {
        this.departHour = departHour;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public String getEtaJam() {
        return etaJam;
    }

    public void setEtaJam(String etaJam) {
        this.etaJam = etaJam;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
