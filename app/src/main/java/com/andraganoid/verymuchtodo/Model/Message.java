package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import java.util.Calendar;

import static com.andraganoid.verymuchtodo.Todo.myself;

public class Message {

    private String text;
    private long timestamp;
    private String from;
    private String title;
    private String id;

    private StringBuilder sb = new StringBuilder();

    public Message() {
    }

    public Message(String text) {
        this.timestamp = System.currentTimeMillis();
        this.text = text;
        this.from = myself.getName();
        this.title = myself.getName() + String.valueOf(timestamp);
        this.id=myself.getId();
    }

    public String getMsgData() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
        sb.setLength(0);
        sb.append(from)
                .append("@")
                .append(date);
        return sb.toString();
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

    public String getTimestampAsString( ) {
        return String.valueOf(timestamp);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
