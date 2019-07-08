package com.andraganoid.verymuchtodo.model;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
//import java.util.HashMap;
import java.util.List;


public class TodoList {



    private String title = "";
    private String description = "";
    private boolean emergency = false;
    private boolean completed = false;
    private List<TodoItem> todoItemList = new ArrayList<>();
    private Long timestamp;
    private User user;


    public TodoList(User user) {
        this.user = user;
    }

    public TodoList(User user, String title, String description, boolean emergency) {

        this.user = user;
        this.timestamp = System.currentTimeMillis();
        this.title = title;
        this.description = description;
        this.emergency = emergency;
    }

    public TodoList() {
    }

//    public Document getDocument() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("title", title);
//        map.put("description", description);
//        map.put("emergency", emergency);
//        map.put("completed", completed);
//        map.put("todoItemList", todoItemList);
//        map.put("timestampt", timestamp);
//        map.put("user", user);
//
//        return new Document(COLLECTION_TODOS, title, map);
//    }


    public String getLastEdit() {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName())
                .append("@")
                .append(getFormattedDate());
        return sb.toString();

    }

    public String getFormattedDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
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

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<TodoItem> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List<TodoItem> todoItemList) {
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

    //    public void makeLastEdit() {
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(Long.parseLong(lastEditTimestamp));
//        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
//        makeLastEdit(date);
//    }

//    public void makeLastEdit(String date) {
//
//        sb.setLength(0);
//        sb.append(myself.getName())
//                .append("@")
//                .append(date);
//        this.lastEdit = sb.toString();
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getShortDescription() {
//        return shortDescription;
//    }
//
//    public void setShortDescription(String shortDescription) {
//        this.shortDescription = shortDescription;
//    }
//
//    public String getLastEdit() {
//        return lastEdit;
//    }
//
//    public boolean isEmergency() {
//        return emergency;
//    }
//
//    public void setEmergency(boolean emergency) {
//        this.emergency = emergency;
//    }
//
//    public boolean isCompleted() {
//        return completed;
//    }
//
//    public void setCompleted(boolean completed) {
//        this.completed = completed;
//    }
//
//    public List<TodoItem> getTodoItemList() {
//        return todoItemList;
//    }
//
//    public void setTodoItemList(List<TodoItem> todoItemList) {
//        this.todoItemList = todoItemList;
//    }
//
//
//    public String getLastEditTimestamp() {
//        return lastEditTimestamp;
//    }
//
//    public void setLastEditTimestamp() {
//        this.lastEditTimestamp = String.valueOf(System.currentTimeMillis());
//    }
//
//    public void setLastEditTimestamp(String ts) {
//        this.lastEditTimestamp = ts;
//        makeLastEdit();
//    }
//
//    public String getLastEditId() {
//        return lastEditId;
//    }
//
//    public void setLastEditId(String lastEditId) {
//        this.lastEditId = lastEditId;
//    }
//
//    public void setLastEdit(String lastEdit) {
//        this.lastEdit = lastEdit;
//    }
//
//    public User getLastEditBy() {
//        return lastEditBy;
//    }
//
//    public void setLastEditBy(User lastEditBy) {
//        this.lastEditBy = lastEditBy;
//    }
//
//    public String getLastEditDate() {
//        return lastEditDate;
//    }


}
