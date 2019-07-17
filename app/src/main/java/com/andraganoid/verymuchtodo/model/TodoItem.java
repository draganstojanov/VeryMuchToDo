package com.andraganoid.verymuchtodo.model;


public class TodoItem {



    private String content = "";
    private boolean completed = false;
    private Long timestamp = 0L;
    private User user;

    public TodoItem() {
    }

    public TodoItem(User user) {
        this.user = user;
    }

    public TodoItem(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getTimestamp() {
        return timestamp;
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
