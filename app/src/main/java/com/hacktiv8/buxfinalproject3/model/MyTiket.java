package com.hacktiv8.buxfinalproject3.model;

public class MyTiket {

    String nameBus, date, asal, tujuan, passenger, totalPrice;

    public MyTiket(){

    }

    public MyTiket(String nameBus, String date, String asal, String tujuan, String passenger, String totalPrice) {
        this.nameBus = nameBus;
        this.date = date;
        this.asal = asal;
        this.tujuan = tujuan;
        this.passenger = passenger;
        this.totalPrice = totalPrice;
    }


    public String getNameBus() {
        return nameBus;
    }

    public void setNameBus(String nameBus) {
        this.nameBus = nameBus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
