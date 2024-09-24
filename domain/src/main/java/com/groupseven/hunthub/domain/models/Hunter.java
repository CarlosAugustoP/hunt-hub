package com.groupseven.hunthub.domain.models;

import javax.persistence.*;
import jakarta.persistence.Entity;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "hunters")
public class Hunter extends User {

    private String linkPortfolio;
    private List<Task> tasks = new ArrayList<>();
    private String bio;
    private String profilePicture;
    private int rating = 0;
    private int level = 0;
    private List<String> certifications = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private List<Achievement> achievements = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public Hunter(Long cpf, String name, String email, String password, String linkPortfolio, List<Task> tasks, String bio, String profilePicture, int rating, int level, List<String> certifications, List<String> links, List<Achievement> achievements, List<Project> projects) {
        super(cpf, name, email, password); 
        this.linkPortfolio = linkPortfolio;
        this.tasks = tasks;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.rating = rating;
        this.level = level;
        this.certifications = certifications;
        this.links = links;
        this.achievements = achievements;
        this.projects = projects;
    }

    public Hunter() {
    }

    public String getLinkPortfolio() {
        return linkPortfolio;
    }

    public void setLinkPortfolio(String linkPortfolio) {
        this.linkPortfolio = linkPortfolio;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void addCertification(String certification) {
        this.certifications.add(certification);
    }

    public void removeCertification(String certification) {
        this.certifications.remove(certification);
    }

    public void addLink(String link) {
        this.links.add(link);
    }

    public void removeLink(String link) {
        this.links.remove(link);
    }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
    }

    public void removeAchievement(Achievement achievement) {
        this.achievements.remove(achievement);
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }
}
