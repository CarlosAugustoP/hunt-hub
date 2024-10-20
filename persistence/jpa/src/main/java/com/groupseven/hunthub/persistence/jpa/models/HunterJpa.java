package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class HunterJpa extends UserJpa {

  private String linkPortfolio;

  @ManyToMany(mappedBy = "hunters")
  private List<TaskJpa> tasks;

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

  public List<TaskJpa> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskJpa> tasks) {
    this.tasks = tasks;
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
}