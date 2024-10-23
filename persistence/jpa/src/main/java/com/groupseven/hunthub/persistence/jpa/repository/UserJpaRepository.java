package com.groupseven.hunthub.persistence.jpa.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
    Optional<UserJpa> findByEmail(String email);
}