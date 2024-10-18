package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TASK")
public class TaskJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public UUID id;

  @ManyToOne
  private POJpa po;

  @ManyToMany
  @JoinTable(name = "TASK_HUNTERS", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "hunter_id"))
  private List<HunterJpa> hunters;

  @ManyToMany
  @JoinTable(name = "TASK_HUNTERS_APPLIED", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "hunter_id"))
  private List<HunterJpa> huntersApplied;


  protected String description;

  protected TaskStatus status;

  protected Date deadline;

  protected int reward;

  protected int numberOfMeetings;

  protected int numberOfHuntersRequired;

  @ElementCollection
  protected List<String> tags;

  protected double ratingRequired;

  protected boolean completed;
}

interface TaskJpaRepository extends JpaRepository<TaskJpa, UUID> {
}

@Repository
class TaskRepositoryImpl implements TaskRepository {
  @Autowired
  TaskJpaRepository repository;

  @Autowired
  TaskMapper taskMapper;

  @Override
  public void save(Task task) {
    TaskJpa taskJpa = taskMapper.toEntity(task);
    repository.save(taskJpa);
  }

  @Override
  public Task findById(UUID id) {
    return repository.findById(id)
            .map(taskMapper::toDomain)
            .orElse(null);
  }

  @Override
  public List<Task> findAll() {
    List<TaskJpa> taskJpaList = repository.findAll();
    return taskJpaList.stream()
            .map(taskMapper::toDomain)
            .toList();
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}