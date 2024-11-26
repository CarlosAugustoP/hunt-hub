package com.groupseven.hunthub.steps.builder;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.steps.interfaces.HunterBuilder;

import java.util.UUID;

public class AlternativeHunterBuilder implements HunterBuilder {
    private final Hunter hunter;

    public AlternativeHunterBuilder() {
        this.hunter = new Hunter();
    }
    public void buildName() {
        this.hunter.setName("Jessie Hunter");
    }
    public void buildEmail() {
        this.hunter.setEmail("jessiehunter@example.com");
    }
    public void buildPassword() {
        this.hunter.setPassword("hunter123");
    }
    public void buildProfilePicture() {
        this.hunter.setProfilePicture("https://example.com/johndoe.jpg");
    }
    public void buildCpf() {
        this.hunter.setCpf("12345678901");
    }

    public void buildTasks() {
        this.hunter.setTasks(null);
    }
    public void buildTotalRating() {
        this.hunter.setTotalRating(0);
    }
    public void buildRatingCount() {
        this.hunter.setRatingCount(0);
    }

    public void buildBio() {
        this.hunter.setBio("Experienced developer.");
    }
    public void buildLevels() {
        this.hunter.setLevel(0);
    }
    public void buildHunterId() {
        this.hunter.setId(UUID.randomUUID());
    }
    public void buildCertifications() {
        this.hunter.setCertifications(null);
    }
    public void buildLinks() {
        this.hunter.setLinks(null);
    }
    public void buildAchievements() {
        this.hunter.setAchievements(null);
    }
    public void buildProjects() {
        this.hunter.setProjects(null);
    }
    public Hunter getHunter() {
        return this.hunter;
    }

}