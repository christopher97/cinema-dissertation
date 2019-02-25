package com.example.cinema_mobile.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


public class Ticket implements Parcelable {

    private int id;
    private int qty;
    private int status;
    private String date;
    private String time;
    private String movieTitle;
    private String cinema;
    private String imageURL;

    public Ticket() {}

    public Ticket(Parcel parcel) {
        Bundle bundle = parcel.readBundle();

        this.id = bundle.getInt("id");
        this.qty = bundle.getInt("qty");
        this.status = bundle.getInt("status");
        this.date = bundle.getString("date");
        this.time = bundle.getString("time");
        this.movieTitle = bundle.getString("movieTitle");
        this.cinema = bundle.getString("cinema");
        this.imageURL = bundle.getString("imageURL");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();

        bundle.putInt("id", id);
        bundle.putInt("qty", qty);
        bundle.putInt("status", status);
        bundle.putString("date", date);
        bundle.putString("time", time);
        bundle.putString("movieTitle", movieTitle);
        bundle.putString("cinema", cinema);
        bundle.putString("imageURL", imageURL);

        parcel.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int i) {
            return new Ticket[i];
        }
    };
}
