package com.groupseven.hunthub.domain.models.dto;

import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Project;

import java.util.List;

public class HunterDetailsDto extends HunterDto {
    private int ratingCount;
    private String linkPortfolio;
    private List<TaskDTO> tasks;

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getLinkPortfolio() {
        return linkPortfolio;
    }

    public void setLinkPortfolio(String linkPortfolio) {
        this.linkPortfolio = linkPortfolio;
    }

    private List<String> certifications;
    private List<Achievement> achievements;
    private List<Project> projects;


    public HunterDetailsDto(String bio, String profilePicture, double totalRating, int levels) {
        super(bio, profilePicture, totalRating, levels);
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

    public static HunterDetailsDto convertToHunterDetailsDto(Hunter hunter) {
        HunterDetailsDto hunterDetailsDto = new HunterDetailsDto(
                hunter.getBio(),
                hunter.getProfilePicture(),
                hunter.getTotalRating(),
                hunter.getLevel()
        );
        hunterDetailsDto.setRatingCount(hunter.getRatingCount());
        hunterDetailsDto.setCertifications(hunter.getCertifications());
        hunterDetailsDto.setAchievements(hunter.getAchievements());
        hunterDetailsDto.setProjects(hunter.getProjects());
        hunterDetailsDto.setLinkPortfolio(hunter.getLinkPortfolio());
        hunterDetailsDto.setId(hunter.getId().getId());
        hunterDetailsDto.setName(hunter.getName());
        hunterDetailsDto.setEmail(hunter.getEmail());
        hunterDetailsDto.setPoints(hunter.getPoints());
        hunterDetailsDto.setTasks(TaskDTO.convertToTaskDTOList(hunter.getTasks()));
        hunterDetailsDto.setRating(hunter.getRating());
        return hunterDetailsDto;
    }
}
