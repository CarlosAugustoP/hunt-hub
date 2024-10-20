package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.HunterMapper;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;

import java.util.List;
import java.util.UUID;

@Repository
public class HunterRepositoryImpl implements HunterRepository {

  @Autowired
  HunterJpaRepository repository;

  @Autowired
  HunterMapper hunterMapper;

  @Override
  public Hunter findById(UUID id) {
    return repository.findById(id)
        .map(hunterMapper::toDomain)
        .orElse(null);
  }

  @Override
  public Hunter findByName(String name) {
    HunterJpa hunterJpa = repository.findByName(name);
    return hunterJpa != null ? hunterMapper.toDomain(hunterJpa) : null;
  }

  @Override
  public void save(Hunter hunter) {
    HunterJpa hunterJpa = hunterMapper.toEntity(hunter);
    repository.save(hunterJpa);
  }

  @Override
  public void delete(Hunter hunter) {
    HunterJpa hunterJpa = hunterMapper.toEntity(hunter);
    repository.delete(hunterJpa);
  }

  @Override
  public List<Hunter> findAll() {
    return repository.findAll().stream()
        .map(hunterMapper::toDomain)
        .toList();
  }
}