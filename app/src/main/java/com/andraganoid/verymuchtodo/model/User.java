package com.andraganoid.verymuchtodo.model;

import android.location.Location;

import java.util.HashMap;

public class User {
    private String id;
    private String name;
    private String email;
  //  private Location location=null;
  //  private Long locationTimestamp=null;


    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
//    public Long getLocationTimestamp() {
//        return locationTimestamp;
//    }
//
//    public void setLocationTimestamp(Long locationTimestamp) {
//        this.locationTimestamp = locationTimestamp;
//    }
}
