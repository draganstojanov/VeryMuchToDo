package com.andraganoid.verymuchtodo.model;

import java.util.ArrayList;


public class TodoList {

    private String title = "";
    private String description = "";
    private boolean emergency = false;
    private boolean completed = false;
    private ArrayList<TodoItem> todoItemList = new ArrayList<>();
    private Long timestamp = 0L;
    private User user;
    private String id;

    public TodoList(User user) {
        this.user = user;
        this.id = "new";
    }

    public TodoList() {
    }

    public void updateList(User mUser) {
        this.user = mUser;
        this.timestamp = System.currentTimeMillis();
        boolean co = true;
        if (todoItemList.size() > 0) {
            for (TodoItem ti : todoItemList) {
                if (!ti.isCompleted()) {
                    co = false;
                    break;
                }
            }
        }
        this.completed = co;
    }

    public void setId() {
        this.id = user.getId() + "-" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ArrayList<TodoItem> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(ArrayList<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
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
