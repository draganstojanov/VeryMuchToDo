package com.andraganoid.verymuchtodo.J.todo.menu;

public class MenuAlert {
    private boolean listAlert;
    private boolean messageAlert;

    public MenuAlert() {
        listAlert = false;
        messageAlert = false;
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
}
