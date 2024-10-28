package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
public class POJpa extends UserJpa {
  private UUID id;

  private int levels;
  private int rating;
  private int totalRating;
  private int ratingCount;
  private String profilePicture;
  private String bio;

  @OneToMany(mappedBy = "po", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
  private List<TaskJpa> tasks;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  // Getters and Setters
  public int getLevels() {
    return levels;
  }

  public void setLevels(int levels) {
    this.levels = levels;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
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

  public List<TaskJpa> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskJpa> tasks) {
    this.tasks = tasks;
  }
}