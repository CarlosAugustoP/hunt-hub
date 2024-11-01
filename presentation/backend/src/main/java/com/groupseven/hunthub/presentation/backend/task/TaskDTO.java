package com.groupseven.hunthub.presentation.backend.task;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;

import java.util.Date;
import java.util.List;

public class TaskDTO {
    private String description;
    private String title;
    private Date deadline;
    private int reward;
    private int numberOfMeetings;
    private int numberOfHuntersRequired;
    private double ratingRequired;
    private List<Tags> tags;

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setDescription(task.getDescription());
        dto.setTitle(task.getTitle());
        dto.setDeadline(task.getDeadline());
        dto.setReward(task.getReward());
        dto.setNumberOfMeetings(task.getNumberOfMeetings());
        dto.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
        dto.setRatingRequired(task.getRatingRequired());
        dto.setTags(task.getTags());
        return dto;
    }

}