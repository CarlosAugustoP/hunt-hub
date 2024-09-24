package com.groupseven.hunthub.domain.models;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.util.UUID;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date startDate;
    private Date endDate;
    private String description;
    private String title;
    private List<String> skills;

    public Project() {
        this.skills = new ArrayList<>();
    }

    public Project(Date startDate, Date endDate, String description, String title, List<String> skills) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.title = title;
        this.skills = skills;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }
}
