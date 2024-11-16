package com.groupseven.hunthub.domain.models.dto;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDetailsDto extends TaskDTO {
    private List<HunterDto> hunters;
    private PODto po;


    public List<HunterDto> getHunters() {
        return hunters;
    }

    public void setHunters(List<HunterDto> hunters) {
        this.hunters = hunters;
    }

    public PODto getPo() {
        return po;
    }

    public void setPo(PODto po) {
        this.po = po;
    }

    public TaskDetailsDto(String description, String title, Date deadline, int reward, int numberOfMeetings, int numberOfHuntersRequired, double ratingRequired, List<Tags> tags, List<HunterDto> hunters, PODto po, UUID id) {
        super(description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired, tags, id);
        this.hunters = hunters;
        this.po = po;

    }

    public static TaskDetailsDto convertToTaskDetailsDTO(Task task) {
        TaskDetailsDto taskDetailsDto = new TaskDetailsDto(
                task.getDescription(),
                task.getTitle(),
                task.getDeadline(),
                task.getReward(),
                task.getNumberOfMeetings(),
                task.getNumberOfHuntersRequired(),
                task.getRatingRequired(),
                task.getTags(),
                HunterDto.convertToHunterDTOList(task.getHunters()),
                PODto.convertToPODTO(task.getPo()),
                task.getId().getId()

        );
        return taskDetailsDto;
    }
}
