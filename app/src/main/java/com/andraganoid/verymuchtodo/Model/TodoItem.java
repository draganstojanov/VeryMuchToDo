package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import java.util.Calendar;

import static com.andraganoid.verymuchtodo.Todo.myself;

public class TodoItem {


    private StringBuilder sb = new StringBuilder();

    private String content;
    private boolean completed;
    private String lastEdit;
    private String lastEditTimestamp;

    public TodoItem() {
    }

    public TodoItem(String content) {
        //  setLastEditTimestamp();
        setLastEdit();
        //  this.content = content;
        setContent(content);
        this.completed = false;

    }

    public void setLastEdit() {
        this.lastEditTimestamp = String.valueOf(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(lastEditTimestamp));
        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();

        sb.setLength(0);
        sb.append(myself.getName())
                .append("@")
                .append(date);
        this.lastEdit = sb.toString();

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
       // setLastEdit();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public String getLastEditTimestamp() {
        return lastEditTimestamp;
    }

    public void setLastEditTimestamp() {
        this.lastEditTimestamp = String.valueOf(System.currentTimeMillis());
    }

}
