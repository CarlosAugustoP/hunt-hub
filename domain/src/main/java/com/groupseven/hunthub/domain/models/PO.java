package com.groupseven.hunthub.domain.models;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import javax.management.Notification;

import com.groupseven.hunthub.domain.interfaces.Observer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PO extends User implements Observer {

    private int levels = 0;
    private int rating = 0;

    private int totalRating = 0;
    private int ratingCount = 0;

    List<Task> tasks = new ArrayList<>();

    String profilePicture;
    String bio;

    public PO(String cpf, String name, String email, String password, List<Task> tasks, String profilePicture,
            String bio) {
        super(name, email, password, cpf, "ROLE_PO", new UserId(UUID.randomUUID()));
        this.levels = 0;
        this.rating = 5;
        this.tasks = tasks;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.totalRating = 0;
        this.ratingCount = 0;
        this.setRole("ROLE_PO");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.getRole()));
    }

    public PO() {
    }

    public int getLevels() {
        return levels;
    }

    public void setId(UUID id) {
        this.id = new UserId(id);
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

    public List<Task> getTasks() {
        return tasks;
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

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
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

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void setUser(User user) {
        this.setId(user.getId().getId());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setPassword(user.getPassword());
        this.setCpf(user.getCpf());
    }

    public User getUser() {
        User user = new User();
        user.setId(this.id.getId());
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setCpf(this.cpf);
        return user;
    }

    public void rate(int rating) {
        this.totalRating += rating;
        this.ratingCount++;
        this.rating = this.totalRating / this.ratingCount;
    }

    @Override
    public void update(String message, String theme, Task task) {
        System.out.println("Notificação para PO: " + name + " - " + message);
    }
}
