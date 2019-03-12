package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import java.util.Calendar;

import static com.andraganoid.verymuchtodo.Todo.myself;

public class Message {

    private String text;
    private long timestamp;
    private String from;
    private String title;

    private StringBuilder sb=new StringBuilder();

    public Message(String text) {
        this.timestamp=System.currentTimeMillis();
        this.text=text;
        this.from=



    }


    public String getMsgData(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
        sb.setLength(0);
        sb.append(myself.getName())
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
}
