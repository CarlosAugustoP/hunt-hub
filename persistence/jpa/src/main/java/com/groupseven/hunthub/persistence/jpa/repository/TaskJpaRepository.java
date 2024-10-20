package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;
import java.util.UUID;

interface TaskJpaRepository extends JpaRepository<TaskJpa, UUID> {
}
