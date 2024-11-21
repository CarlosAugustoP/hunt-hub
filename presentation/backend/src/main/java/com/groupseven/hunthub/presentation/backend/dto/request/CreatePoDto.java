package com.groupseven.hunthub.presentation.backend.dto.request;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Project;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.groupseven.hunthub.domain.models.Achievement;

import java.util.ArrayList;
import java.util.List;
public class CreatePoDto {
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

    @NotNull(message = "O rating é obrigatório.")
    private double rating;

    private List<String> certifications;

    private List<String> links;

    private List<Achievement> achievements;

    private List<Project> projects;

    // Getters e Setters

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

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    // Método para converter DTO para PO
    public PO convertToPO() {
        PO po = new PO();
        po.setName(this.name);
        po.setCpf(this.cpf);
        po.setEmail(this.email);
        po.setPassword(this.password);
        po.setBio(this.bio);
        po.setProfilePicture(this.profilePicture);
        po.setRating(5);
        po.setTasks(new ArrayList<>());
        return po;
    }
}
