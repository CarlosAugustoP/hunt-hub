package com.groupseven.hunthub.presentation.backend.dto.response;

import com.groupseven.hunthub.domain.models.Hunter;

import java.util.List;
import java.util.UUID;

public class HunterResponseDto extends UserResponseDto {
    private String bio;
    private String profilePicture;
    private double totalRating;
    private int levels;
    public UUID id;
    public double rating;

    public HunterResponseDto(String bio, String profilePicture, double totalRating, int levels) {
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.totalRating = totalRating;
        this.levels = levels;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static HunterResponseDto convertToHunterDTO(Hunter hunter) {
        HunterResponseDto hunterResponseDto = new HunterResponseDto(
                hunter.getBio(),
                hunter.getProfilePicture(),
                hunter.getRating(),
                hunter.getLevel()
        );

        hunterResponseDto.setName(hunter.getName());
        hunterResponseDto.setEmail(hunter.getEmail());
        hunterResponseDto.setId(hunter.getId().getId());
        hunterResponseDto.setPoints(hunter.getPoints());
        hunterResponseDto.setRating(hunter.getRating());

        return hunterResponseDto;
    }

    public static List<HunterResponseDto> convertToHunterDTOList(List<Hunter> hunters) {
        return hunters.stream()
                .map(HunterResponseDto::convertToHunterDTO)
                .toList();
    }
}
