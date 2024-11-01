package com.groupseven.hunthub.persistence.jpa.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class TaskJpa {

  @Id
  private UUID id = UUID.randomUUID();

  private UUID poId;

  @ElementCollection
  @CollectionTable(name = "task_hunters", joinColumns = @JoinColumn(name = "task_id"))
  @Column(name = "hunter_id")
  private List<UUID> hunterIds;

  @ElementCollection
  @CollectionTable(name = "task_hunters_applied", joinColumns = @JoinColumn(name = "task_id"))
  @Column(name = "hunter_id")
  private List<UUID> hunterAppliedIds;

  private String description;

  private String title;

  private TaskStatusJPA status;

  private Date deadline;

  private int reward;

  private int numberOfMeetings;

  private int numberOfHuntersRequired;

  @ElementCollection
  private List<TagsJPA> tags;

  private double ratingRequired;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getPoId() {
    return poId;
  }

  public void setPoId(UUID poId) {
    this.poId = poId;
  }

  public List<UUID> getHunterIds() {
    return hunterIds;
  }

  public void setHunterIds(List<UUID> hunterIds) {
    this.hunterIds = hunterIds;
  }

  public List<UUID> getHunterAppliedIds() {
    return hunterAppliedIds;
  }

  public void setHunterAppliedIds(List<UUID> hunterAppliedIds) {
    this.hunterAppliedIds = hunterAppliedIds;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() { return title; }

  public void setTitle(String title) { this.title = title; }

  public TaskStatusJPA getStatus() {
    return status;
  }

  public void setStatus(TaskStatusJPA status) {
    this.status = status;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public int getReward() {
    return reward;
  }

  public void setReward(int reward) {
    this.reward = reward;
  }

  public int getNumberOfMeetings() {
    return numberOfMeetings;
  }

  public void setNumberOfMeetings(int numberOfMeetings) {
    this.numberOfMeetings = numberOfMeetings;
  }

  public int getNumberOfHuntersRequired() {
    return numberOfHuntersRequired;
  }

  public void setNumberOfHuntersRequired(int numberOfHuntersRequired) {
    this.numberOfHuntersRequired = numberOfHuntersRequired;
  }

  public List<TagsJPA> getTagsJPA() {
    return tags;
  }

  public void setTagsJPA(List<TagsJPA> tags) {
    this.tags = tags;
  }

  public double getRatingRequired() {
    return ratingRequired;
  }

  public void setRatingRequired(double ratingRequired) {
    this.ratingRequired = ratingRequired;
  }

  public void addTag(TagsJPA tag) {
    tags.add(tag);
  }

  public void removeTag(com.groupseven.hunthub.domain.models.Tags tag) {
    tags.remove(tag);
  }

  public TagsJPA getTag(int index) {
    return tags.get(index);
  }



  }
