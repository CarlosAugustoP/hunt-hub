package com.groupseven.hunthub.steps.builder;

import com.groupseven.hunthub.domain.models.Hunter;

import java.util.*;

public class BasicHunterBuilder  {
    private Hunter hunter;

    private final Map<String, Hunter> predefinedHunters = new HashMap<>();

    private String name;
    private String email;
    private String cpf;
    private String bio;


    public BasicHunterBuilder() {
        this.hunter = new Hunter();
            createPredefinedHunters();
    }

    public void buildName() {
        this.hunter.setName(name);
    }

    public void buildEmail() {
        this.hunter.setEmail(email);
    }

    public void buildPassword() {
        this.hunter.setPassword("hunter123");
    }

    public void buildProfilePicture() {
        this.hunter.setProfilePicture("https://example.com/default.jpg");
    }

    public void buildCpf() {
        this.hunter.setCpf(cpf);
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
        this.hunter.setBio(bio);
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

    // Método para criar múltiplos Hunters
    private void createPredefinedHunters() {

        Hunter hunter0 = new Hunter();
        hunter0.setName("John Doe");
        hunter0.setEmail("johndoe@exemplo.com");
        hunter0.setCpf("12345678901");
        hunter0.setBio("Frontend expert.");
        hunter0.setBio("Experienced developer.");
        hunter0.setId(UUID.randomUUID());
        predefinedHunters.put("John", hunter0);

        Hunter hunter1 = new Hunter();
        hunter1.setName("Jessie Hunter");
        hunter1.setEmail("jessiehunter@example.com");
        hunter1.setCpf("12345678901");
        hunter1.setBio("Desenvolvedor experiente");
        hunter1.setId(UUID.randomUUID());
        predefinedHunters.put("Jessie", hunter1);

        Hunter hunter2 = new Hunter();
        hunter2.setName("Alex Hunter");
        hunter2.setEmail("alexhunter@example.com");
        hunter2.setCpf("12345678902");
        hunter2.setBio("Caçador de soluções");
        hunter2.setId(UUID.randomUUID());
        predefinedHunters.put("Alex", hunter2);
    }

    public Hunter getSpecificHunter(String name) {
        return predefinedHunters.get(name);
    }

    public Map<String, Hunter> getAllPredefinedHunters() {
        return predefinedHunters;
    }
}
