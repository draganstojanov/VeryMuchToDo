package com.andraganoid.verymuchtodo.todo.menu;

public class TodoBars {
    private String title;
    private String subtitle;

    public TodoBars(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
