package com.andraganoid.verymuchtodo.model;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.andraganoid.verymuchtodo.todo.Todo.myself;

public class TodoList {

    private StringBuilder sb = new StringBuilder();

    private String title;
    private String shortDescription;
    private String lastEdit;
    private boolean emergency;
    private boolean completed;
    private List <TodoItem> todoItemList;
    private String lastEditTimestamp;
    private String lastEditId;

    public TodoList() {
    }

    public TodoList(String title, String shortDescription, boolean emergency) {
        setLastEditTimestamp();
        makeLastEdit();
        this.lastEditId = myself.getId();
        this.title = title;
        this.shortDescription = shortDescription;
        this.emergency = emergency;
        this.completed = false;
        this.todoItemList = new ArrayList <>();
    }

    public void makeLastEdit() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(lastEditTimestamp));
        String date = DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
        makeLastEdit(date);
    }

    public void makeLastEdit(String date) {

        sb.setLength(0);
        sb.append(myself.getName())
                .append("@")
                .append(date);
        this.lastEdit = sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLastEdit() {
        return lastEdit;
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

    public List <TodoItem> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List <TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
    }


    public String getLastEditTimestamp() {
        return lastEditTimestamp;
    }

    public void setLastEditTimestamp() {
        this.lastEditTimestamp = String.valueOf(System.currentTimeMillis());
    }

    public void setLastEditTimestamp(String ts) {
        this.lastEditTimestamp = ts;
        makeLastEdit();
    }

    public String getLastEditId() {
        return lastEditId;
    }

    public void setLastEditId(String lastEditId) {
        this.lastEditId = lastEditId;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }
}
