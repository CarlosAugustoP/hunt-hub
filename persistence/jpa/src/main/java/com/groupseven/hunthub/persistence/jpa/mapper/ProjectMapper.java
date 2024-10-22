package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Project;
import com.groupseven.hunthub.domain.models.ProjectId;
import com.groupseven.hunthub.persistence.jpa.models.ProjectJpa;

import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

  public ProjectJpa toEntity(Project project) {
    ProjectJpa projectJpa = new ProjectJpa();
    projectJpa.setStartDate(project.getStartDate());
    projectJpa.setEndDate(project.getEndDate());
    projectJpa.setDescription(project.getDescription());
    projectJpa.setTitle(project.getTitle());
    projectJpa.setSkills(project.getSkills());
    return projectJpa;
  }

  public Project toDomain(ProjectJpa projectJpa) {
    ProjectId newProjectId = new ProjectId(projectJpa.getId());
    return new Project(
        projectJpa.getStartDate(),
        projectJpa.getEndDate(),
        projectJpa.getDescription(),
        projectJpa.getTitle(),
        projectJpa.getSkills(),
        newProjectId);
  }
}