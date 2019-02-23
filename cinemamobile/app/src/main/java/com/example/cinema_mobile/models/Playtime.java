package com.example.cinema_mobile.models;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class Playtime {

    private int id;
    private int movieID;
    private Time time;

    public Playtime() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String toString() {
        return time.toString();
    }
}
