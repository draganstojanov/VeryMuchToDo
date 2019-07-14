package com.andraganoid.verymuchtodo.todo.menu;

public class MenuAlert {
    private boolean listAlert;
    private boolean messageAlert;
    private boolean userAlert;
    private boolean mapAlert;


    public MenuAlert() {
        listAlert = true;
        messageAlert = false;
        userAlert = false;
        mapAlert = false;
    }

    public boolean isListAlert() {
        return listAlert;
    }

    public void setListAlert(boolean listAlert) {
        this.listAlert = listAlert;
    }

    public boolean isMessageAlert() {
        return messageAlert;
    }

    public void setMessageAlert(boolean messageAlert) {
        this.messageAlert = messageAlert;
    }

    public boolean isUserAlert() {
        return userAlert;
    }

    public void setUserAlert(boolean userAlert) {
        this.userAlert = userAlert;
    }

    public boolean isMapAlert() {
        return mapAlert;
    }

    public void setMapAlert(boolean mapAlert) {
        this.mapAlert = mapAlert;
    }


}
