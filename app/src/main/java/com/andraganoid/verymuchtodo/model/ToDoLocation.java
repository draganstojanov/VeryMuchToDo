package com.andraganoid.verymuchtodo.model;

import android.location.Location;

public class ToDoLocation {

    private double latitude;
    private double longitude;
    private Long timestamp;
    private User user;

    public ToDoLocation(User user, Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.timestamp = location.getTime();
        this.user = user;
    }


    public ToDoLocation() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
