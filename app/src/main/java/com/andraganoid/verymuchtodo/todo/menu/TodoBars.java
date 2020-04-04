package com.andraganoid.verymuchtodo.todo.menu;

public class TodoBars {
    private String title;
    private String subtitle;
    private boolean emergency;

    public TodoBars(String title, boolean emergency) {
        this.title = title;
        this.emergency = emergency;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isEmergency() {
        return emergency;
    }

}
