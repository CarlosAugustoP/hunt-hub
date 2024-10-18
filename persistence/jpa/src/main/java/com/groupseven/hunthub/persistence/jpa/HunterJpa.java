package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.Entity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.HunterRepository;

import java.util.ArrayList;

@Entity
public class HunterJpa extends UserJpa {
  DecimalFormat df = new DecimalFormat("#.00");

  private List<TaskJpa> tasks;

  private String bio;

  private String profilePicture;

  private double rating;

  private int level;

  private int totalRating;

  private int ratingCount;

  private List<String> certifications;

  private List<Achievement> achievements;

  private List<ProjectJpa> projects;
}

interface HunterJpaRepository extends JpaRepository<HunterJpa, UUID> {
}

@Repository
class HunterRepositoryImpl implements HunterRepository {
  @Autowired
  HunterJpaRepository repository;

  @Override
  public Hunter findById(UUID id) {
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Hunter findByName(String name) {
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

  @Override
  public void save(Hunter hunter) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void delete(Hunter hunter) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public List<Hunter> findAll() {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }
}