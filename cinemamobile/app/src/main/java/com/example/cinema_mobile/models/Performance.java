package com.example.cinema_mobile.models;

import java.util.Date;

public class Performance {

    private int id;
    private int playtimeID;
    private int cinemaID;
    private Date date;
    private int totalSeat;
    private int bookedSeat;

    public Performance() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaytimeID() {
        return playtimeID;
    }

    public void setPlaytimeID(int playtimeID) {
        this.playtimeID = playtimeID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public int getAvailableSeat() { return totalSeat-bookedSeat; }
}
