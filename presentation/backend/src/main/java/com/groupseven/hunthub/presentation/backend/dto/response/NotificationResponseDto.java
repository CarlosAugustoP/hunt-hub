package com.groupseven.hunthub.presentation.backend.dto.response;

import java.time.LocalDate;

public class NotificationResponseDto {

    private String theme;
    private String message;
    private LocalDate createdAt;

    public NotificationResponseDto(String theme, String message, LocalDate createdAt) {
        this.theme = theme;
        this.message = message;
        this.createdAt = createdAt;
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
}