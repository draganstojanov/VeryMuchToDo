package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import java.util.List;

import androidx.databinding.BindingAdapter;

public class TodoList {

//    private final String ITEM_CAT_SHOP_LIST="Shopping list";
//    private final String ITEM_CAT_REMINDER="Reminder";

    private StringBuilder sb;

    public String name;
    public String shortDescription;
    public long createdTimestamp;
    public long editedTimestamp;
    public String createdByUser;
    public String editedByUser;
    // private int category;
    public boolean emergency;
    public boolean completed;
    public String createdLine;
    public String editedLine;

    public List <TodoItem> todoItemList;

    public TodoList() {
    }

    //Test


    public TodoList(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

    public void setCreatedLine() {
        sb.setLength(0);
        sb.append(getTimeString(createdTimestamp))
                .append(" by ")
                .append(createdByUser);

        createdLine = sb.toString();
    }

    public void setEditedLine() {
        sb.setLength(0);
        sb.append(getTimeString(editedTimestamp))
                .append(" by ")
                .append(editedByUser);

        editedLine = sb.toString();
    }


    private String getTimeString(long ts) {
        return DateFormat.format("dd.MM.yyyy hh:mm", new java.util.Date(ts)).toString();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getEditedTimestamp() {
        return editedTimestamp;
    }

    public void setEditedTimestamp(long editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getEditedByUser() {
        return editedByUser;
    }

    public void setEditedByUser(String editedByUser) {
        this.editedByUser = editedByUser;
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

    public String getCreatedLine() {
        return createdLine;
    }

    public String getEditedLine() {
        return editedLine;
    }
}
