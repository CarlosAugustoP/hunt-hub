package com.groupseven.hunthub.persistence.jpa;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK")
public class TaskJpa {
  @Id
  public UUID id;

  private POJpa po;

  private String description;

  private TaskStatus status;

  private Date deadline;

  private int reward;

  private int numberOfMeetings;

  private int numberOfHuntersRequired;

  private List<String> tags;

  private List<HunterJpa> hunters;

  private List<HunterJpa> huntersApplied;

  private double ratingRequired;

  private boolean completed;
}

interface TaskJpaRepository extends JpaRepository<TaskJpa, UUID> {
}

@Repository
class TaskRepositoryImpl implements TaskRepository {
  @Autowired
  TaskJpaRepository repository;

  @Override
  public void save(Task task) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public Task findById(java.util.UUID id) {
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<Task> findAll() {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public void delete(java.util.UUID id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}