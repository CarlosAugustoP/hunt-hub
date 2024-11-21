package com.groupseven.hunthub.presentation.backend.dto.response;

import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Project;

import java.util.List;

public class HunterDetailsResponseDto extends HunterResponseDto {
    private int ratingCount;
    private String linkPortfolio;
    private List<TaskResponseDto> tasks;

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


    public HunterDetailsResponseDto(String bio, String profilePicture, double totalRating, int levels) {
        super(bio, profilePicture, totalRating, levels);
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

    public static HunterDetailsResponseDto convertToHunterDetailsDto(Hunter hunter) {
        HunterDetailsResponseDto hunterDetailsResponseDto = new HunterDetailsResponseDto(
                hunter.getBio(),
                hunter.getProfilePicture(),
                hunter.getTotalRating(),
                hunter.getLevel()
        );
        hunterDetailsResponseDto.setRatingCount(hunter.getRatingCount());
        hunterDetailsResponseDto.setCertifications(hunter.getCertifications());
        hunterDetailsResponseDto.setAchievements(hunter.getAchievements());
        hunterDetailsResponseDto.setProjects(hunter.getProjects());
        hunterDetailsResponseDto.setLinkPortfolio(hunter.getLinkPortfolio());
        hunterDetailsResponseDto.setId(hunter.getId().getId());
        hunterDetailsResponseDto.setName(hunter.getName());
        hunterDetailsResponseDto.setEmail(hunter.getEmail());
        hunterDetailsResponseDto.setPoints(hunter.getPoints());
        hunterDetailsResponseDto.setTasks(TaskResponseDto.convertToTaskDTOList(hunter.getTasks()));
        hunterDetailsResponseDto.setRating(hunter.getRating());
        return hunterDetailsResponseDto;
    }
}
