package com.groupseven.hunthub.domain.models;

import javax.persistence.*;
import javax.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pos")
public class PO extends User { 

    @NotNull
    int levels = 0;

    @NotNull
    int rating = 0;

    @OneToMany(mappedBy = "po")
    List<Task> tasks = new ArrayList<>();
    String profilePicture;
    String bio;


    public PO (Long cpf, String name, String email, String password, int levels, int rating, List<Task> tasks, String profilePicture, String bio){
        super(cpf, name, email, password);
        this.levels = levels;
        this.rating = rating;
        this.tasks = tasks;
        this.profilePicture = profilePicture;
        this.bio = bio;
    }

    public PO(){
    }

    public int getLevels() {
        return levels;
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

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void removeTask(Task task){
        this.tasks.remove(task);
    }


}
