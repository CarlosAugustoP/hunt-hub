package com.groupseven.hunthub.domain.models.dto;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PODto extends UserDTO {
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

    public PODto(int levels, int totalRating, String profilePicture, String bio) {
        this.levels = levels;
        this.totalRating = totalRating;
        this.profilePicture = profilePicture;
        this.bio = bio;
    }

   public static PODto convertToPODTO(PO po) {
        PODto poDto = new PODto(
                po.getLevels(),
                po.getTotalRating(),
                po.getProfilePicture(),
                po.getBio()
        );

        poDto.setName(po.getName());
        poDto.setEmail(po.getEmail());
        poDto.setId(po.getId().getId());
        poDto.setPoints(po.getPoints());
        poDto.setRating(po.getRating());

        return poDto;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static List<PODto> convertToPODTOList(List<PO> poList) {
        List<PODto> poDtoList = new ArrayList<>();
        for (PO po : poList) {
            poDtoList.add(convertToPODTO(po));
        }
        return poDtoList;
    }

}
