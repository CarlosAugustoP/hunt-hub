package com.groupseven.hunthub.domain.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.List;
import java.util.ArrayList;

public class Hunter extends User {

    private String linkPortfolio;

    private List<Task> tasks = new ArrayList<>();
    
    private String bio;
    private String profilePicture;
    private int rating = 0;
    private int level = 0;
    private int totalRating = 0; 
    private int ratingCount = 0;
    
    private List<String> certifications = new ArrayList<>();
    
    private List<String> links = new ArrayList<>();
    
    private List<Achievement> achievements = new ArrayList<>();
    
    private List<Project> projects = new ArrayList<>();

    public Hunter(Long cpf, String name, String email, String password, String linkPortfolio, List<Task> tasks, String bio, String profilePicture, int level, List<String> certifications, List<String> links, List<Achievement> achievements, List<Project> projects,int rating, int ratingCount, int totalRating) {
        super(name, email, password, cpf);
        this.linkPortfolio = linkPortfolio;
        this.tasks = tasks;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.rating=rating;
        this.ratingCount = ratingCount;
        this.totalRating = totalRating;
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

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5.");
        }
        this.totalRating += rating;
        this.ratingCount++;
        this.rating = (int) getAverageRating();
    }

    public double getAverageRating() {
        if (ratingCount == 0) {
            return 0;
        }
        return (double) totalRating / ratingCount;
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
    public void ratePO(PO po, int rating) {
        if (this.getCpf() != po.getCpf()) {
            po.rate(rating);
            this.addRating(rating);
        } else {
            throw new IllegalArgumentException("Um hunter não pode se autoavaliar.");
        }
    }
    public void rate(int rating) {
        this.totalRating += rating;
        this.ratingCount++;
        this.rating = this.totalRating / this.ratingCount;
    }
}
