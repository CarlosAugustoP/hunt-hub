package com.groupseven.hunthub.domain.models;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne
    private PO po;

    @NotNull
    @Size(min = 20, max = 2000)
    private String description;

    @NotNull
    @Size(min = 2, max = 20)
    private String title;

    @NotNull
    private String status;

    private Date deadline;

    @NotNull
    private int reward = 50;

    private int numberOfMeetings;

    @NotNull
    private int numberOfHuntersRequired;

    @ManyToMany 
    private List<Hunter> hunters = new ArrayList<>();

    public Task(PO po, String description, String title, String status, Date deadline, int reward, int numberOfMeetings, int numberOfHuntersRequired) {
        this.po = po;
        this.description = description;
        this.title = title;
        this.status = status;
        this.deadline = deadline;
        this.reward = reward;
        this.numberOfMeetings = numberOfMeetings;
        this.numberOfHuntersRequired = numberOfHuntersRequired;
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
            throw new IllegalStateException("Cannot add more hunters. The required number of hunters is already reached.");
        }
    }

    public List<Hunter> getHunters() {
        return hunters;
    }

    public void setHunters(List<Hunter> hunters) {
        this.hunters = hunters;
    }
}
