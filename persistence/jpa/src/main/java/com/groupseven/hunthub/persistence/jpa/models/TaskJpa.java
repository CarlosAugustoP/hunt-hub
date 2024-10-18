package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TASK")
public class TaskJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public UUID id;

  @ManyToOne
  public POJpa po;

  @ManyToMany
  @JoinTable(name = "TASK_HUNTERS", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "hunter_id"))
  public List<HunterJpa> hunters;

  @ManyToMany
  @JoinTable(name = "TASK_HUNTERS_APPLIED", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "hunter_id"))
  public List<HunterJpa> huntersApplied;

  public String description;

  public TaskStatus status;

  public Date deadline;

  public int reward;

  public int numberOfMeetings;

  public int numberOfHuntersRequired;

  @ElementCollection
  public List<String> tags;

  public double ratingRequired;

  public boolean completed;
}