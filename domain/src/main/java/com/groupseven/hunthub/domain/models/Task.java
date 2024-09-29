package com.groupseven.hunthub.domain.models;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

public class Task {

    private UUID id;

    private PO po;

    private String description;

    private String title;

    private String status = "started";

    private Date deadline;

    private int reward = 50;

    private int numberOfMeetings;

    private int numberOfHuntersRequired;

    private List<Hunter> hunters = new ArrayList<>();

    private double ratingRequired;

    private boolean completed;
    public Task(PO po, String description, String title, Date deadline, int reward, int numberOfMeetings,
            int numberOfHuntersRequired, double ratingRequired) {
        this.po = po;
        this.description = description;
        this.title = title;
        this.deadline = deadline;
        this.reward = reward;
        this.numberOfMeetings = numberOfMeetings;
        this.numberOfHuntersRequired = numberOfHuntersRequired;
        this.ratingRequired = ratingRequired;
    }

    public Task() {
        this.hunters = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
            throw new IllegalStateException(
                    "Cannot add more hunters. The required number of hunters is already reached.");
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
}

