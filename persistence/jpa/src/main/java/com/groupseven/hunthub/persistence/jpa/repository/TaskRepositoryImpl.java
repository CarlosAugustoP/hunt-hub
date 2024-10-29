package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.TaskMapper;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
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

  @Override
  public void applyHunterToTask(UUID taskId, Hunter hunter ) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void acceptHunter(UUID taskId, Hunter hunter) {
        throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void declineHunter(UUID taskId, Hunter hunter) {
        throw new UnsupportedOperationException("Not implemented yet");
  }

}