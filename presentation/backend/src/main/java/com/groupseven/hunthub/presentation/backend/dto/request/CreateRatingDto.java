package com.groupseven.hunthub.presentation.backend.dto.request;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;


public class CreateRatingDto {

    @Max(value = 5, message = "A nota deve ser entre 0 e 5.")
    @NotNull(message = "A nota é obrigatória.")
    private int rating;

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
