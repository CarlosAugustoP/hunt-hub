package com.groupseven.hunthub.domain.models;

import java.time.LocalDate;

public class Notification {
    private User user;
    private String theme;
    private String message;
    private LocalDate createdAt;

    /*
    * TODO: RECEBE HUNTER OU PO NO LUGAR DE USER
    *  TIRAR PRIVATE User user
    *  AO INVÉS DE USER.GETID TEM QUE SER HUNTER.GETID.GETID
    *
    * */

    public Notification(String message, String theme, User user) {
        this.message = message;
        this.theme = theme;
        this.user = user;
        this.createdAt = LocalDate.now();
    }

    public Notification() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
