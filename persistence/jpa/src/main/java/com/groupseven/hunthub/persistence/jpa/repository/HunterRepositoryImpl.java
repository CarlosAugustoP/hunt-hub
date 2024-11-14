package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.UserId;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.HunterMapper;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class HunterRepositoryImpl implements HunterRepository {

  @Autowired
  private HunterJpaRepository repository;

  @Autowired
  private HunterMapper hunterMapper;

  @Override
  public Hunter findById(UUID id) {
    HunterJpa hunterJpa = repository.findById(id).orElse(null);
    return hunterJpa != null ? hunterMapper.toDomain(hunterJpa, new ArrayList<>()) : null;
  }

  @Override
  public Hunter findByName(String name) {
    HunterJpa hunterJpa = repository.findByName(name);
    return hunterJpa != null ? hunterMapper.toDomain(hunterJpa, new ArrayList<>()) : null;
  }

  @Override
  public void save(Hunter hunter) {
    HunterJpa hunterJpa = hunterMapper.toEntity(hunter, extractTaskIds(hunter));
    HunterJpa savedHunterJpa = repository.save(hunterJpa);
    hunter.setId(new UserId(savedHunterJpa.getId()).getId());
  }

  @Override
  public void delete(Hunter hunter) {
    HunterJpa hunterJpa = hunterMapper.toEntity(hunter, extractTaskIds(hunter));
    repository.delete(hunterJpa);
  }

  @Override
  public List<Hunter> findAll() {
    List<HunterJpa> hunterJpas = repository.findAll();
    List<Hunter> hunters = new ArrayList<>();
    for (HunterJpa hunterJpa : hunterJpas) {
      hunters.add(hunterMapper.toDomain(hunterJpa, new ArrayList<>()));
    }
    return hunters;
  }

  // Método auxiliar para extrair IDs das Tasks
  private List<UUID> extractTaskIds(Hunter hunter) {
    List<UUID> taskIds = new ArrayList<>();
    if (hunter.getTasks() != null) {
      for (Task task : hunter.getTasks()) {
        taskIds.add(task.getId().getId());
      }
    }
    return taskIds;
  }
}
