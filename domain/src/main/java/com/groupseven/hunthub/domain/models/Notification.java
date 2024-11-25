package com.groupseven.hunthub.domain.models;

import java.util.UUID;
import java.time.LocalDate;

public class Notification {
    private final Hunter hunter;
    private final PO po;
    private String theme;
    private String message;
    private LocalDate createdAt;
    private Task task;

    public Notification(String message, String theme, Hunter hunter, PO po, Task task) {
        if (hunter == null && po == null) {
            throw new IllegalArgumentException("A notificação precisa de pelo menos um Hunter ou PO.");
        }

        this.message = message;
        this.theme = theme;
        this.hunter = hunter;
        this.po = po;
        this.task = task;
        this.createdAt = LocalDate.now();
    }

    public Notification(String message, String theme, Hunter hunter, Task task) {
        this(message, theme, hunter, null, task);
    }

    public Notification(String message, String theme, PO po, Task task) {
        this(message, theme, null, po, task);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UUID getHunterId() {
        return hunter != null ? hunter.getId().getId() : null;
    }

    public UUID getPoId() {
        return po != null ? po.getId().getId() : null;
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

    public Hunter getHunter() {
        return hunter;
    }

    public Hunter setHunter(Hunter hunter) {
        return this.hunter;
    }

    public PO setPO(PO po) {
        return this.po;
    }

    public PO getPo() {
        return po;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
