package com.groupseven.hunthub.presentation.backend.dto.request;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

public class CreateTaskDto {

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotNull(message = "A data de prazo é obrigatória.")
    @Future(message = "O prazo deve ser uma data futura.")
    private Date deadline;

    @Positive(message = "A recompensa deve ser um valor positivo.")
    private int reward;

    @PositiveOrZero(message = "O número de reuniões deve ser zero ou maior.")
    private int numberOfMeetings;

    @Positive(message = "O número de hunters necessários deve ser maior que zero.")
    private int numberOfHuntersRequired;

    @PositiveOrZero(message = "A avaliação mínima deve ser zero ou maior.")
    private double ratingRequired;

    private List<Tags> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getNumberOfMeetings() {
        return numberOfMeetings;
    }

    public void setNumberOfMeetings(int numberOfMeetings) {
        this.numberOfMeetings = numberOfMeetings;
    }

    public int getNumberOfHuntersRequired() {
        return numberOfHuntersRequired;
    }

    public void setNumberOfHuntersRequired(int numberOfHuntersRequired) {
        this.numberOfHuntersRequired = numberOfHuntersRequired;
    }

    public double getRatingRequired() {
        return ratingRequired;
    }

    public void setRatingRequired(double ratingRequired) {
        this.ratingRequired = ratingRequired;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Task convertToTask(PO po) {
        Task task = new Task();
        task.setPo(po); // PO é passado como argumento, pois não faz parte do DTO
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDeadline(this.deadline);
        task.setReward(this.reward);
        task.setNumberOfMeetings(this.numberOfMeetings);
        task.setNumberOfHuntersRequired(this.numberOfHuntersRequired);
        task.setRatingRequired(this.ratingRequired);
        task.setTags(this.tags); // Tags são opcionais
        task.setStatus(TaskStatus.PENDING); // Status inicial padrão
        return task;
    }
}
