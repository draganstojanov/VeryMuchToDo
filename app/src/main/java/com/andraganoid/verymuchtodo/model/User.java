package com.andraganoid.verymuchtodo.model;

import android.location.Location;

import java.util.HashMap;

public class User {
    private String id;
    private String name;
    private String email;
    private Location location;
    private Long locationTimestamp;
    final String COLLECTION_USERS = "colUsers";


    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = null;
        this.locationTimestamp = null;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getLocationTimestamp() {
        return locationTimestamp;
    }

    public void setLocationTimestamp(Long locationTimestamp) {
        this.locationTimestamp = locationTimestamp;
    }

    public Document getDocument() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("email", email);
        map.put("location", location);
        map.put("locationTimestamp", locationTimestamp);

        return new Document(COLLECTION_USERS, id, map);
    }
}
