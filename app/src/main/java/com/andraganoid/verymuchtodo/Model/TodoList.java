package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

import static com.andraganoid.verymuchtodo.Todo.myself;

public class TodoList {


    private StringBuilder sb = new StringBuilder();

    private String title;
    private String shortDescription;
    private String lastEdit;
    private boolean emergency;
    private boolean completed;

    private List <TodoItem> todoItemList;

    public TodoList() {
    }


    public TodoList(String title, String shortDescription, boolean emergency) {
        setLastEdit();
        this.title = title;
        this.shortDescription = shortDescription;
        this.emergency = emergency;
        this.completed = false;
        this.todoItemList = new ArrayList <>();
    }

    public void setLastEdit() {
        sb.setLength(0);
        sb.append(myself.getName())
                .append("@")
                .append(DateFormat.format("dd.MM.yyyy HH:mm", new java.util.Date(System.currentTimeMillis())).toString());
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

    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }
}
