package com.groupseven.hunthub.presentation.backend.dto.response;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDetailsResponseDto extends TaskResponseDto {
    private List<HunterResponseDto> hunters;
    private PoResponseDto po;

    public List<HunterResponseDto> getHunters() {
        return hunters;
    }

    public void setHunters(List<HunterResponseDto> hunters) {
        this.hunters = hunters;
    }

    public PoResponseDto getPo() {
        return po;
    }

    public void setPo(PoResponseDto po) {
        this.po = po;
    }

    public TaskDetailsResponseDto(String description, String title, Date deadline, int numberOfMeetings, int reward, int numberOfHuntersRequired, double ratingRequired, List<Tags> tags, List<HunterResponseDto> hunters, PoResponseDto po, UUID id, TaskStatus taskStatus) {
        super(description, title, deadline, numberOfMeetings, reward, numberOfHuntersRequired, ratingRequired, tags, id, taskStatus);
        this.hunters = hunters;
        this.po = po;
    }

    public static TaskDetailsResponseDto convertToTaskDetailsDTO(Task task) {
        TaskDetailsResponseDto taskDetailsResponseDto = new TaskDetailsResponseDto(
                task.getDescription(),
                task.getTitle(),
                task.getDeadline(),
                task.getNumberOfMeetings(),
                task.getReward(),
                task.getNumberOfHuntersRequired(),
                task.getRatingRequired(),
                task.getTags(),
                HunterResponseDto.convertToHunterDTOList(task.getHunters()),
                PoResponseDto.convertToPODTO(task.getPo()),
                task.getId().getId(),
                task.getStatus()
        );

        return taskDetailsResponseDto;
    }
}
