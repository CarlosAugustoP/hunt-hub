package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Project;
import com.groupseven.hunthub.domain.repository.ProjectRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.ProjectMapper;
import com.groupseven.hunthub.persistence.jpa.models.ProjectJpa;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  @Autowired
  ProjectJpaRepository repository;

  @Autowired
  ProjectMapper projectMapper;

  @Override
  public void save(Project project) {
    ProjectJpa projectJpa = projectMapper.toEntity(project);
    repository.save(projectJpa);
  }

  @Override
  public Project findById(UUID id) {
    return repository.findById(id)
        .map(projectMapper::toDomain)
        .orElse(null);
  }

  @Override
  public List<Project> findAll() {
    List<ProjectJpa> projectJpaList = repository.findAll();
    return projectJpaList.stream()
        .map(projectMapper::toDomain)
        .toList();
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}