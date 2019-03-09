package com.andraganoid.verymuchtodo.Model;

import android.text.format.DateFormat;

import static com.andraganoid.verymuchtodo.Todo.myself;

public class TodoItem {


    private StringBuilder sb = new StringBuilder();

    private String content;
    private boolean completed;
    private String lastEdit;

    public TodoItem() {
    }

    public TodoItem(String content) {
        this.content = content;
        this.completed = false;

    }

    public void setLastEdit() {
        sb.setLength(0);
        sb.append(myself.getName())
                .append("@")
                .append(DateFormat.format("dd.MM.yyyy HH:mm", new java.util.Date(System.currentTimeMillis())).toString());
        this.lastEdit = sb.toString();
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

    public String getLastEdit() {
        return lastEdit;
    }

}
