package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.ProjectJpa;
import java.util.UUID;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpa, UUID> {
}