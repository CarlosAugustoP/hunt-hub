package com.groupseven.hunthub.persistence.jpa.models;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.*;

@Entity
@Table(name = "PROJECT")
public class ProjectJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  private String description;
  private String title;

  @ElementCollection
  @CollectionTable(name = "PROJECT_SKILLS", joinColumns = @JoinColumn(name = "project_id"))
  @Column(name = "skill")
  private List<String> skills;

  // Getters and Setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

}

interface ProjectJpaRepository extends JpaRepository<ProjectJpa, UUID> {
}