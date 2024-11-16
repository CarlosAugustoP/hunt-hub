package com.groupseven.hunthub.domain.models.dto;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;

import java.util.ArrayList;
import java.util.List;

public class PoDetailsDto extends PODto{
    private int ratingCount;
    List<TaskDTO> tasks = new ArrayList<>();

    public PoDetailsDto(int levels, int totalRating, String profilePicture, String bio) {
        super(levels, totalRating, profilePicture, bio);
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public static PoDetailsDto convertToPoDetailsDto(PO po) {
        PoDetailsDto poDetailsDto = new PoDetailsDto(
                po.getLevels(),
                po.getTotalRating(),
                po.getProfilePicture(),
                po.getBio()
        );
        poDetailsDto.setRatingCount(po.getRatingCount());
        poDetailsDto.setTasks(TaskDTO.convertToTaskDTOList(po.getTasks()));
        return poDetailsDto;
    }
}
