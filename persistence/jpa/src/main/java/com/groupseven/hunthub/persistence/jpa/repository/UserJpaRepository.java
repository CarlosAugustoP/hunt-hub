package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
}