package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.Entity;
import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.PoRepository;

@Entity
public class POJpa extends UserJpa {
  private int levels;

  private int rating;

  private int totalRating;

  private int ratingCount;

  private String profilePicture;

  private String bio;

  private List<TaskJpa> tasks;
}

interface POJpaRepository extends JpaRepository<POJpa, UUID> {
}

@Repository
class PORepositoryImpl implements PoRepository {
  @Autowired
  POJpaRepository repository;

  @Override
  public void save(PO po) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }
}
