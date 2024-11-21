package com.groupseven.hunthub.presentation.backend.dto.request;

import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import java.util.List;

public class CreateHunterDto {
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;

    @Size(max = 255, message = "O link do portfólio deve ter no máximo 255 caracteres.")
    private String linkPortfolio;

    @Size(max = 500, message = "A bio deve ter no máximo 500 caracteres.")
    private String bio;

    @Size(max = 255, message = "O link da foto de perfil deve ter no máximo 255 caracteres.")
    private String profilePicture;

    private List<String> certifications;

    private List<String> links;

    private List<Achievement> achievements;

    private List<Project> projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLinkPortfolio() {
        return linkPortfolio;
    }

    public void setLinkPortfolio(String linkPortfolio) {
        this.linkPortfolio = linkPortfolio;
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

    public Hunter convertToHunter() {
        Hunter hunter = new Hunter();
        hunter.setName(this.name);
        hunter.setCpf(this.cpf);
        hunter.setEmail(this.email);
        hunter.setPassword(this.password);
        hunter.setLinkPortfolio(this.linkPortfolio);
        hunter.setBio(this.bio);
        hunter.setProfilePicture(this.profilePicture);
        hunter.setCertifications(this.certifications);
        hunter.setLinks(this.links);
        hunter.setAchievements(this.achievements);
        hunter.setProjects(this.projects);
        hunter.setRating(5.0);
        return hunter;
    }
}
