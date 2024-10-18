package com.groupseven.hunthub.persistence.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.repository.AchievementRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACHIEVEMENT")
public class AchievementJpa {
  private Long id;

  private String name;
  private String description;
  private String icon;
}

interface AchievementJpaRepository extends JpaRepository<AchievementJpa, Long> {
}

@Repository
class AchievementRepositoryImpl implements AchievementRepository {
  @Autowired
  AchievementJpaRepository repository;
}
