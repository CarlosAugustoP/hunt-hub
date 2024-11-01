package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class HunterJpa extends UserJpa {
  public HunterJpa() {
    super();
  }
  private String linkPortfolio;

  @ElementCollection
  @CollectionTable(name = "hunter_tasks", joinColumns = @JoinColumn(name = "hunter_id"))
  @Column(name = "task_id")
  private List<UUID> taskIds;

  private String bio;
  private String profilePicture;
  private double rating;
  private int level;
  private int totalRating;
  private int ratingCount;

  @ElementCollection
  private List<String> certifications;

  @ElementCollection
  private List<String> links;

  @OneToMany
  private List<AchievementJpa> achievements;

  @OneToMany
  private List<ProjectJpa> projects;

  // Getters e Setters
  public String getLinkPortfolio() {
    return linkPortfolio;
  }

  public void setLinkPortfolio(String linkPortfolio) {
    this.linkPortfolio = linkPortfolio;
  }

  public List<UUID> getTaskIds() {
    return taskIds;
  }

  public void setTaskIds(List<UUID> taskIds) {
    this.taskIds = taskIds;
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

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getTotalRating() {
    return totalRating;
  }

  public void setTotalRating(int totalRating) {
    this.totalRating = totalRating;
  }

  public int getRatingCount() {
    return ratingCount;
  }

  public void setRatingCount(int ratingCount) {
    this.ratingCount = ratingCount;
  }

  public List<String> getCertifications() {
    return certifications;
  }

  public void setCertifications(List<String> certifications) {
    this.certifications = certifications;
  }

  public List<String> getLinks() {
    return links;
  }

  public void setLinks(List<String> links) {
    this.links = links;
  }

  public List<AchievementJpa> getAchievements() {
    return achievements;
  }

  public void setAchievements(List<AchievementJpa> achievements) {
    this.achievements = achievements;
  }

  public List<ProjectJpa> getProjects() {
    return projects;
  }

  public void setProjects(List<ProjectJpa> projects) {
    this.projects = projects;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HunterJpa)) return false;
    return super.equals(o);
  }
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}