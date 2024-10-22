package com.groupseven.hunthub.domain.models;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

public class Task {

    private TaskId id;

    private PO po;

    private String description;

    private String title;

    // open, closed, completed
    private String status = "open";

    private Date deadline;

    private int reward = 50;

    private int numberOfMeetings;

    private int numberOfHuntersRequired;

    private List<Tags> tags = new ArrayList<>();

    private List<Hunter> hunters = new ArrayList<>();
    private List<Hunter> huntersApplied = new ArrayList<>();
    private double ratingRequired;

    private boolean completed;
    public Task(PO po, String description, String title, Date deadline, int reward, int numberOfMeetings,
            int numberOfHuntersRequired, double ratingRequired, List<Tags> tags, TaskId id) {
        this.po = po;
        this.description = description;
        this.title = title;
        this.deadline = deadline;
        this.reward = reward;
        this.numberOfMeetings = numberOfMeetings;
        this.numberOfHuntersRequired = numberOfHuntersRequired;
        this.ratingRequired = ratingRequired;
        this.tags = tags;
        this.id = id;
    }

    public Task() {
        this.hunters = new ArrayList<>();
    }

    public TaskId getId() {
        return id;
    }

    public void setId(TaskId id) {
        this.id = id;
    }

    public PO getPo() {
        return po;
    }

    public void setPo(PO po) {
        this.po = po;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void removeHunter(Hunter hunter) {
        hunters.remove(hunter);
    }

    public void removeHunter(int index) {
        hunters.remove(index);
    }

    public void addHunter(Hunter hunter) {
        if (hunters.size() < numberOfHuntersRequired) {
            hunters.add(hunter);
        } else {
            throw new IllegalStateException("Cannot add more hunters. The required number of hunters is already reached.");
        }
    }

    public List<Hunter> getHunters() {
        return hunters;
    }

    public void setHunters(List<Hunter> hunters) {
        this.hunters = hunters;
    }

    public double getRatingRequired() {
        return ratingRequired;
    }

    public void setRatingRequired(double ratingRequired) {
        this.ratingRequired = ratingRequired;
    }
    public void setCompleted(boolean completed) {
        this.status = "completed";
    }
    public boolean isCompleted() {
        return completed;
    }

    public List<Hunter> getHuntersApplied() {
        return huntersApplied;
    }

    public void setHuntersApplied(List<Hunter> huntersApplied) {
        this.huntersApplied = huntersApplied;
    }

    public void applyHunter(Hunter hunter) {
        huntersApplied.add(hunter);
    }

    public void assignHunter(Hunter hunter) {
        hunters.add(hunter);
    }

    public void addTag(Tags tag) {
        tags.add(tag);
    }

    public void removeTag(Tags tag) {
        tags.remove(tag);
    }

    public Tags getTag(int index) {
        return tags.get(index);
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}

