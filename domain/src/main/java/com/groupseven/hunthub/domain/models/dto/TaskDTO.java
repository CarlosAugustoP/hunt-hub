package com.groupseven.hunthub.domain.models.dto;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDTO {
        private String description;
        private String title;
        private Date deadline;
        private int reward;
        private int numberOfMeetings;
        private int numberOfHuntersRequired;
        private double ratingRequired;
        private UUID id;

        public TaskDTO(String description, String title, Date deadline, int numberOfMeetings, int reward, int numberOfHuntersRequired, double ratingRequired, List<Tags> tags, UUID id) {
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

        public TaskDTO() {

        }

        private List<Tags> tags;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static List<TaskDTO> convertToTaskDTOList(List<Task> tasks) {
            List<TaskDTO> taskDTOS = new ArrayList<>();
            for (Task task : tasks) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setDescription(task.getDescription());
                taskDTO.setTitle(task.getTitle());
                taskDTO.setDeadline(task.getDeadline());
                taskDTO.setReward(task.getReward());
                taskDTO.setNumberOfMeetings(task.getNumberOfMeetings());
                taskDTO.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
                taskDTO.setRatingRequired(task.getRatingRequired());
                taskDTO.setTags(task.getTags());
                taskDTOS.add(taskDTO);
            }
            return taskDTOS;
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
            dto.setId(task.getId().getId());
            return dto;
        }

    }

