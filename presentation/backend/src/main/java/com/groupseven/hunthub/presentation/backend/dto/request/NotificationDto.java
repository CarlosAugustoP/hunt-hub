package com.groupseven.hunthub.presentation.backend.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public class NotificationDto {

    private UUID id;
    private String theme;
    private String message;
    private LocalDate createdAt;
    private UUID hunterId;
    private UUID poId;
    private UUID taskId;

    public NotificationDto(UUID id, String theme, String message, LocalDate createdAt, UUID hunterId, UUID poId,
            UUID taskId) {
        this.id = id;
        this.theme = theme;
        this.message = message;
        this.createdAt = createdAt;
        this.hunterId = hunterId;
        this.poId = poId;
        this.taskId = taskId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getHunterId() {
        return hunterId;
    }

    public void setHunterId(UUID hunterId) {
        this.hunterId = hunterId;
    }

    public UUID getPoId() {
        return poId;
    }

    public void setPoId(UUID poId) {
        this.poId = poId;
    }
}