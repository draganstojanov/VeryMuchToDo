package com.andraganoid.verymuchtodo.model;

import android.text.format.DateFormat;

import java.util.Calendar;

import static com.andraganoid.verymuchtodo.todo.Todo.myself;

public class TodoItem {


    private StringBuilder sb = new StringBuilder();

    private String content;
    private boolean completed;
   // private String lastEdit;
    private Long timestamp;
    private User user;


    public TodoItem() {
    }

    public TodoItem(User user) {
        this.user = user;
        this.content="";
        this.completed = false;
    }

    public TodoItem(String content) {
        setLastEdit();
        setContent(content);
        this.completed = false;

    }

    public void setLastEdit() {
      //  this.timestamp = String.valueOf(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();

        sb.setLength(0);
        sb.append(myself.getName())
                .append("@")
                .append(date);
      //  this.lastEdit = sb.toString();

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
       // makeLastEdit();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

 //   public String getLastEdit() {
      //  return lastEdit;
   // }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setLastEditTimestamp() {
        this.timestamp = (System.currentTimeMillis());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
