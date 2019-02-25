package com.example.cinema_mobile.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private int runningTime;
    private String language;
    private Date releaseDate;
    private String synopsis;
    private String imageURL;
    private int ticketPrice;
    private String distributor;
    private String rating;
    private Date startDate;
    private Date endDate;

    private ArrayList<String> casts;
    private ArrayList<String> genres;
    private ArrayList<String> directors;

    public Movie() {}

    public Movie(Parcel parcel) {
        Bundle bundle = parcel.readBundle();

        this.id = bundle.getInt("id");
        this.title = bundle.getString("title", title);
        this.runningTime = bundle.getInt("runningTime");
        this.language = bundle.getString("language");
        this.releaseDate = new Date(bundle.getLong("releaseDate"));
        this.synopsis = bundle.getString("synopsis");
        this.imageURL = bundle.getString("imageURL");
        this.distributor = bundle.getString("distributor");
        this.ticketPrice = bundle.getInt("ticketPrice");
        this.rating = bundle.getString("rating");
        this.casts = bundle.getStringArrayList("casts");
        this.genres = bundle.getStringArrayList("genres");
        this.directors = bundle.getStringArrayList("directors");
        this.startDate = new Date(bundle.getLong("startDate"));
        this.endDate = new Date(bundle.getLong("endDate"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public int getTicketPrice() { return ticketPrice; }

    public void setTicketPrice(int ticketPrice) { this.ticketPrice = ticketPrice; }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<String> casts) {
        this.casts = casts;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        bundle.putInt("runningTime", runningTime);
        bundle.putString("language", language);
        bundle.putLong("releaseDate", releaseDate.getTime());
        bundle.putString("synopsis", synopsis);
        bundle.putString("imageURL", imageURL);
        bundle.putInt("ticketPrice", ticketPrice);
        bundle.putString("distributor", distributor);
        bundle.putString("rating", rating);
        bundle.putStringArrayList("casts", casts);
        bundle.putStringArrayList("genres", genres);
        bundle.putStringArrayList("directors", directors);
        bundle.putLong("startDate", startDate.getTime());
        bundle.putLong("endDate", endDate.getTime());

        parcel.writeBundle(bundle);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
