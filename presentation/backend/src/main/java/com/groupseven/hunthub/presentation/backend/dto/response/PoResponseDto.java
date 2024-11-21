package com.groupseven.hunthub.presentation.backend.dto.response;

import com.groupseven.hunthub.domain.models.PO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PoResponseDto extends UserResponseDto {
    private int levels;
    private int totalRating;

    public UUID getId() {
        return id;
    }

    private String profilePicture;
    private String bio;
    public UUID id;
    public double rating;

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public PoResponseDto(int levels, int totalRating, String profilePicture, String bio) {
        this.levels = levels;
        this.totalRating = totalRating;
        this.profilePicture = profilePicture;
        this.bio = bio;
    }

   public static PoResponseDto convertToPODTO(PO po) {
        PoResponseDto poResponseDto = new PoResponseDto(
                po.getLevels(),
                po.getTotalRating(),
                po.getProfilePicture(),
                po.getBio()
        );

        poResponseDto.setName(po.getName());
        poResponseDto.setEmail(po.getEmail());
        poResponseDto.setId(po.getId().getId());
        poResponseDto.setPoints(po.getPoints());
        poResponseDto.setRating(po.getRating());

        return poResponseDto;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static List<PoResponseDto> convertToPODTOList(List<PO> poList) {
        List<PoResponseDto> poResponseDtoList = new ArrayList<>();
        for (PO po : poList) {
            poResponseDtoList.add(convertToPODTO(po));
        }
        return poResponseDtoList;
    }

}
