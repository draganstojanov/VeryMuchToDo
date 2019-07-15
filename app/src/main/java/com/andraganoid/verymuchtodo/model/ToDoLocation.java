package com.andraganoid.verymuchtodo.model;

import android.location.Location;

public class ToDoLocation {

    private Location location;
    private Long timestamp;
    private User user;

    public ToDoLocation(User user, Location location) {
        this.location = location;
        this.timestamp = System.currentTimeMillis();
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
