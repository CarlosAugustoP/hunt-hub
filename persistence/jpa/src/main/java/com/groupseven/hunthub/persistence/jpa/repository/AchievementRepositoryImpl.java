package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.domain.repository.AchievementRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.AchievementMapper;
import com.groupseven.hunthub.persistence.jpa.models.AchievementJpa;


import java.util.List;
import java.util.Optional;

@Repository
public class AchievementRepositoryImpl implements AchievementRepository {

  @Autowired
  AchievementJpaRepository repository;

  @Autowired
  AchievementMapper achievementMapper;

  @Override
  public void save(Achievement achievement) {
    AchievementJpa achievementJpa = achievementMapper.toEntity(achievement);
    repository.save(achievementJpa);
  }

  @Override
  public Optional<Achievement> findById(Long id) {
    return repository.findById(id)
        .map(achievementMapper::toDomain);
  }

  @Override
  public List<Achievement> findAll() {
    return repository.findAll().stream()
        .map(achievementMapper::toDomain)
        .toList();
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}