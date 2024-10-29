package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "task")
public class TaskJpa {

  @Id
  private UUID id = UUID.randomUUID();


  @ManyToOne
  private POJpa po;

  @ManyToMany
  @JoinTable(
          name = "task_hunters",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false),
          inverseJoinColumns = @JoinColumn(name = "hunter_id", referencedColumnName = "id") // Make sure this points to the 'id' in UserJpa
  )
  private List<HunterJpa> hunters;

  @ManyToMany
  @JoinTable(
          name = "task_hunters_applied",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false),
          inverseJoinColumns = @JoinColumn(name = "hunter_id", referencedColumnName = "id") // Make sure this points to the 'id' in UserJpa
  )
  private List<HunterJpa> huntersApplied;

  private String description;

  private TaskStatusJPA status;

  private Date deadline;

  private int reward;

  private int numberOfMeetings;

  private int numberOfHuntersRequired;

  @ElementCollection
  private List<TagsJPA> tags;

  private double ratingRequired;

  private boolean completed;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public POJpa getPo() {
    return po;
  }

  public void setPo(POJpa po) {
    this.po = po;
  }

  public List<HunterJpa> getHunters() {
    return hunters;
  }

  public void setHunters(List<HunterJpa> hunters) {
    this.hunters = hunters;
  }

  public List<HunterJpa> getHuntersApplied() {
    return huntersApplied;
  }

  public void setHuntersApplied(List<HunterJpa> huntersApplied) {
    this.huntersApplied = huntersApplied;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

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

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
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
