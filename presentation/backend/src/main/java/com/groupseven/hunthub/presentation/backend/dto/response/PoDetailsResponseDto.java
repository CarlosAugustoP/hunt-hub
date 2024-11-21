package com.groupseven.hunthub.presentation.backend.dto.response;
import com.groupseven.hunthub.domain.models.PO;

import java.util.ArrayList;
import java.util.List;

public class PoDetailsResponseDto extends PoResponseDto {
    private int ratingCount;
    List<TaskResponseDto> tasks = new ArrayList<>();

    public PoDetailsResponseDto(int levels, int totalRating, String profilePicture, String bio) {
        super(levels, totalRating, profilePicture, bio);
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<TaskResponseDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponseDto> tasks) {
        this.tasks = tasks;
    }

    public static PoDetailsResponseDto convertToPoDetailsDto(PO po) {
        PoDetailsResponseDto poDetailsDto = new PoDetailsResponseDto(
                po.getLevels(),
                po.getTotalRating(),
                po.getProfilePicture(),
                po.getBio()
        );
        poDetailsDto.setRatingCount(po.getRatingCount());
        poDetailsDto.setTasks(TaskResponseDto.convertToTaskDTOList(po.getTasks()));
        poDetailsDto.setId(po.getId().getId());
        poDetailsDto.setEmail(po.getEmail());
        poDetailsDto.setName(po.getName());
        poDetailsDto.setPoints(po.getPoints());
        poDetailsDto.setRating(po.getRating());

        return poDetailsDto;
    }
}
