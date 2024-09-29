package com.groupseven.hunthub.domain.models;

import java.time.LocalDate;
import java.util.Date;

public class Notification {
    private User user;
    private String theme;
    private String message;
    private LocalDate createdAt;

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
