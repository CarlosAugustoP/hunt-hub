package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.POMapper;
import com.groupseven.hunthub.persistence.jpa.models.POJpa;

import java.util.List;
import java.util.UUID;

@Repository
public class PORepositoryImpl implements PoRepository {

  @Autowired
  POJpaRepository repository;

  @Autowired
  POMapper poMapper;

  @Override
  public void save(PO po) {
    POJpa poJpa = poMapper.toEntity(po);
    repository.save(poJpa);
  }

  @Override
  public PO findById(UUID id) {
    return repository.findById(id)
        .map(poMapper::toDomain)
        .orElse(null);
  }

  @Override
  public List<PO> findAll() {
    return repository.findAll().stream()
        .map(poMapper::toDomain)
        .toList();
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}