package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.RatingCheck;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.persistence.jpa.models.RatingCheckJpa;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

public interface RatingCheckJpaRepository extends JpaRepository<RatingCheckJpa, UUID> {

    Collection<RatingCheckJpa> findByTaskId(UUID taskId);
}
