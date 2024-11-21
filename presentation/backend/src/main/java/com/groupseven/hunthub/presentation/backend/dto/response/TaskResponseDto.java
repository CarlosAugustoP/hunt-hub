package com.groupseven.hunthub.presentation.backend.dto.response;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskResponseDto {
        private String description;
        private String title;
        private Date deadline;
        private int reward;
        private int numberOfMeetings;
        private int numberOfHuntersRequired;
        private double ratingRequired;
        private UUID id;

        public TaskResponseDto(String description, String title, Date deadline, int numberOfMeetings, int reward, int numberOfHuntersRequired, double ratingRequired, List<Tags> tags, UUID id) {
            this.description = description;
            this.title = title;
            this.deadline = deadline;
            this.numberOfMeetings = numberOfMeetings;
            this.reward = reward;
            this.numberOfHuntersRequired = numberOfHuntersRequired;
            this.ratingRequired = ratingRequired;
            this.tags = tags;
            this.id = id;
        }

        public TaskResponseDto() {

        }

        private List<Tags> tags;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static List<TaskResponseDto> convertToTaskDTOList(List<Task> tasks) {
            List<TaskResponseDto> taskResponseDtos = new ArrayList<>();
            for (Task task : tasks) {
                TaskResponseDto taskResponseDto = new TaskResponseDto();
                taskResponseDto.setDescription(task.getDescription());
                taskResponseDto.setTitle(task.getTitle());
                taskResponseDto.setDeadline(task.getDeadline());
                taskResponseDto.setReward(task.getReward());
                taskResponseDto.setNumberOfMeetings(task.getNumberOfMeetings());
                taskResponseDto.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
                taskResponseDto.setRatingRequired(task.getRatingRequired());
                taskResponseDto.setTags(task.getTags());
                taskResponseDtos.add(taskResponseDto);
            }
            return taskResponseDtos;
        }

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

        public TaskResponseDto convertToDTO(Task task) {
            TaskResponseDto dto = new TaskResponseDto();
            dto.setDescription(task.getDescription());
            dto.setTitle(task.getTitle());
            dto.setDeadline(task.getDeadline());
            dto.setReward(task.getReward());
            dto.setNumberOfMeetings(task.getNumberOfMeetings());
            dto.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
            dto.setRatingRequired(task.getRatingRequired());
            dto.setTags(task.getTags());
            dto.setId(task.getId().getId());
            return dto;
        }

    }

