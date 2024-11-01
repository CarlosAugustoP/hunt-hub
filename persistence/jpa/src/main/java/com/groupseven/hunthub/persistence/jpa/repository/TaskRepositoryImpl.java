package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.TaskMapper;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  @Autowired
  private TaskJpaRepository repository;

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private ObjectProvider<PoRepository> poRepositoryProvider; // Injeção tardia de PoRepository

  @Autowired
  private ObjectProvider<HunterRepository> hunterRepositoryProvider; // Injeção tardia de HunterRepository

  @Override
  public void save(Task task) {
    UUID poId = task.getPo() != null ? task.getPo().getId().getId() : null;

    List<UUID> hunterIds = new ArrayList<>();
    if (task.getHunters() != null) {
      for (Hunter hunter : task.getHunters()) {
        hunterIds.add(hunter.getId().getId());
      }
    }

    List<UUID> hunterAppliedIds = new ArrayList<>();
    if (task.getHuntersApplied() != null) {
      for (Hunter hunter : task.getHuntersApplied()) {
        hunterIds.add(hunter.getId().getId());
      }
    }

    TaskJpa taskJpa = taskMapper.toEntity(task, poId, hunterIds, hunterAppliedIds);
    repository.save(taskJpa);
  }

  @Override
  public Task findById(UUID id) {
    TaskJpa taskJpa = repository.findById(id).orElse(null);
    if (taskJpa != null) {
      PO po = poRepositoryProvider.getIfAvailable().findById(taskJpa.getPoId());

      List<Hunter> hunters = new ArrayList<>();
      if (taskJpa.getHunterIds() != null) {
        for (UUID hunterId : taskJpa.getHunterIds()) {
          Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hunterId);
          if (hunter != null) {
            hunters.add(hunter);
          }
        }
      }

      List<Hunter> huntersApplied = new ArrayList<>();
      if (taskJpa.getHunterAppliedIds() != null) {
        for (UUID hunterId : taskJpa.getHunterAppliedIds()) {
          Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hunterId);
          if (hunter != null) {
            huntersApplied.add(hunter);
          }
        }
      }

      return taskMapper.toDomain(taskJpa, po, hunters, huntersApplied);
    }
    return null;
  }

  @Override
  public List<Task> findAll() {
    List<TaskJpa> taskJpaList = repository.findAll();
    List<Task> tasks = new ArrayList<>();
    for (TaskJpa taskJpa : taskJpaList) {
      PO po = poRepositoryProvider.getIfAvailable().findById(taskJpa.getPoId());

      List<Hunter> hunters = new ArrayList<>();
      if (taskJpa.getHunterIds() != null) {
        for (UUID hunterId : taskJpa.getHunterIds()) {
          Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hunterId);
          if (hunter != null) {
            hunters.add(hunter);
          }
        }
      }

      List<Hunter> huntersApplied = new ArrayList<>();
      if (taskJpa.getHunterAppliedIds() != null) {
        for (UUID hunterId : taskJpa.getHunterAppliedIds()) {
          Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hunterId);
          if (hunter != null) {
            huntersApplied.add(hunter);
          }
        }
      }

      tasks.add(taskMapper.toDomain(taskJpa, po, hunters, huntersApplied));
    }
    return tasks;
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }

  @Override
  public void applyHunterToTask(UUID taskId, Hunter hunter) {
    Task task = findById(taskId);
    if (task != null) {
      task.applyHunter(hunter);
      save(task);
    } else {
      throw new IllegalArgumentException("Task not found with ID: " + taskId);
    }
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
