package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {

  void save(Project project);

  Project findById(UUID id);

  List<Project> findAll();

  void delete(UUID id);
}