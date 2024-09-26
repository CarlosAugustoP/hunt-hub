package com.groupseven.hunthub.domain.models;

public class Notification {
    private User user;
    private String theme;
    private String message;

    public Notification(String message, String theme, User user) {
        this.message = message;
        this.theme = theme;
        this.user = user;
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
}
