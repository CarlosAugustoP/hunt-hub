package com.groupseven.hunthub.persistence.jpa.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.TaskMapper;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  @Autowired
  private TaskJpaRepository repository;

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private ObjectProvider<PoRepository> poRepositoryProvider;

  @Autowired
  private ObjectProvider<HunterRepository> hunterRepositoryProvider;

  @PersistenceContext
  private EntityManager entityManager;

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
        hunterAppliedIds.add(hunter.getId().getId());
      }
    }

    TaskJpa taskJpa = taskMapper.toEntity(task, poId, hunterIds, hunterAppliedIds);
    System.out.println("estou aqui task repositoryImpl");
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
  public List<Task> findTasksNotAppliedByHunter(UUID hunterId) {
      List<TaskJpa> taskJpaList = repository.findAll();
      List<Task> tasks = new ArrayList<>();

      for (TaskJpa taskJpa : taskJpaList) {
          if (!taskJpa.getHunterAppliedIds().contains(hunterId)) {
              PO po = poRepositoryProvider.getIfAvailable().findById(taskJpa.getPoId());

              List<Hunter> hunters = new ArrayList<>();
              if (taskJpa.getHunterIds() != null) {
                  for (UUID hId : taskJpa.getHunterIds()) {
                      Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hId);
                      if (hunter != null) {
                          hunters.add(hunter);
                      }
                  }
              }

              List<Hunter> huntersApplied = new ArrayList<>();
              if (taskJpa.getHunterAppliedIds() != null) {
                  for (UUID hId : taskJpa.getHunterAppliedIds()) {
                      Hunter hunter = hunterRepositoryProvider.getIfAvailable().findById(hId);
                      if (hunter != null) {
                          huntersApplied.add(hunter);
                      }
                  }
              }

              tasks.add(taskMapper.toDomain(taskJpa, po, hunters, huntersApplied));
          }
      }
      return tasks;
  }

  @Override
  @Transactional
  public void applyHunterToTask(UUID taskId, Hunter hunter) {
      TaskJpa task = entityManager.find(TaskJpa.class, taskId);
      if (task != null) {
          if (task.getHunterAppliedIds().contains(hunter.getId().getId())) {
              throw new IllegalStateException("Hunter has already applied to this task.");
          }
          task.applyHunter(hunter.getId().getId());
          entityManager.merge(task);
      } else {
          throw new IllegalArgumentException("Task not found with ID: " + taskId);
      }
  }



  @Transactional
  @Override
  public void acceptHunter(UUID taskId, Hunter hunterId) {
    TaskJpa task = entityManager.find(TaskJpa.class, taskId);
    if (task != null) {
      task.assignHunter(hunterId.getId().getId());
      entityManager.merge(task);
    } else {
      throw new IllegalArgumentException("Task not found with ID: " + taskId);
    }
  }

  @Transactional
  @Override
  public void declineHunter(UUID taskId, Hunter hunterId) {
    TaskJpa task = entityManager.find(TaskJpa.class, taskId);
    if (task != null) {
      task.refuseHunter(hunterId.getId().getId());
      entityManager.merge(task);
    } else {
      throw new IllegalArgumentException("Task not found with ID: " + taskId);
    }
  }

}
