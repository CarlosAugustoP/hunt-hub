package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;

import java.util.UUID;

public interface HunterJpaRepository extends JpaRepository<HunterJpa, UUID> {
  HunterJpa findByName(String name);

}