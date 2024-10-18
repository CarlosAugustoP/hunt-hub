package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.repository.ProjectRepository;

import java.util.List;
import java.util.Date;

@Entity
@Table(name = "PROJECT")
public class ProjectJpa {
  private UUID id;

  private Date startDate;
  private Date endDate;
  private String description;
  private String title;
  private List<String> skills;
}

interface ProjectJpaRepository extends JpaRepository<ProjectJpa, UUID> {
}

@Repository
class ProjectRepositoryImpl implements ProjectRepository {
  @Autowired
  ProjectJpaRepository repository;
}
