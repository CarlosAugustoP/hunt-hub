package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;

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

  @ElementCollection
  @CollectionTable(name = "po_tasks", joinColumns = @JoinColumn(name = "po_id"))
  @Column(name = "task_id")
  private List<UUID> taskIds;

  public List<UUID> getTaskIds() {
    return taskIds;
  }

  public void setTaskIds(List<UUID> taskIds) {
    this.taskIds = taskIds;
  }

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
}