package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.POMapper;
import com.groupseven.hunthub.persistence.jpa.models.POJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PORepositoryImpl implements PoRepository {

  @Autowired
  private POJpaRepository repository;

  @Autowired
  private POMapper poMapper;

  @Autowired
  private TaskRepository taskRepository; // Injetar TaskRepository

  @Override
  public void save(PO po) {
    // Obter os IDs das Tasks associadas
    List<UUID> taskIds = new ArrayList<>();
    if (po.getTasks() != null) {
      for (Task task : po.getTasks()) {
        taskIds.add(task.getId().getId());
      }
    }
    POJpa poJpa = poMapper.toEntity(po, taskIds);
    repository.save(poJpa);
  }

  @Override
  public PO findById(UUID id) {
    POJpa poJpa = repository.findById(id).orElse(null);
    if (poJpa != null) {
      // Recuperar as Tasks associadas ao PO
      List<Task> tasks = new ArrayList<>();
      if (poJpa.getTaskIds() != null) {
        for (UUID taskId : poJpa.getTaskIds()) {
          Task task = taskRepository.findById(taskId);
          if (task != null) {
            tasks.add(task);
          }
        }
      }
      return poMapper.toDomain(poJpa, tasks);
    }
    return null;
  }

  @Override
  public List<PO> findAll() {
    List<POJpa> poJpaList = repository.findAll();
    List<PO> poList = new ArrayList<>();
    for (POJpa poJpa : poJpaList) {
      // Recuperar as Tasks associadas ao PO
      List<Task> tasks = new ArrayList<>();
      if (poJpa.getTaskIds() != null) {
        for (UUID taskId : poJpa.getTaskIds()) {
          Task task = taskRepository.findById(taskId);
          if (task != null) {
            tasks.add(task);
          }
        }
      }
      poList.add(poMapper.toDomain(poJpa, tasks));
    }
    return poList;
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}
