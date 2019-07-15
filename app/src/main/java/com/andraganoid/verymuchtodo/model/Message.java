package com.andraganoid.verymuchtodo.model;

public class Message {

    private String text;
    private long timestamp;
    private User user;
    private String id;
    // private ArrayList<User> receiver;
    // private String title; GROUP/ROOM???


    public Message() {
    }

    public Message(User user, String text) {
        this.text = text;
        this.timestamp = System.currentTimeMillis();
        this.user = user;
        this.id = user.getId() + "-" + timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
