package com.example.cinema_mobile.models;

import android.support.annotation.NonNull;

public class Cinema {

    private int id;
    private String name;
    private int seat_capacity;

    public Cinema() {}

    public Cinema(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.seat_capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeat_capacity() {
        return seat_capacity;
    }

    public void setSeat_capacity(int seat_capacity) {
        this.seat_capacity = seat_capacity;
    }

    @NonNull
    public String toString() {
        return this.name;
    }
}
