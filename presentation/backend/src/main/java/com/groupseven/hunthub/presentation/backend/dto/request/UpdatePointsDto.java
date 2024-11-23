package com.groupseven.hunthub.presentation.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


public class UpdatePointsDto {

    @NotNull(message = "O campo 'points' é obrigatório.")
    @PositiveOrZero(message = "O campo 'points' deve ser um valor maior ou igual a zero.")
    private Integer points;

    // Getters e Setters
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
