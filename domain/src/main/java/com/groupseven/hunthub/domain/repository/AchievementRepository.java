package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.Achievement;
import java.util.List;
import java.util.Optional;

public interface AchievementRepository {

  void save(Achievement achievement);

  Optional<Achievement> findById(Long id);

  List<Achievement> findAll();

  void delete(Long id);
}