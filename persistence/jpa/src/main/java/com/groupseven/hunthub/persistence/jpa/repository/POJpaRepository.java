package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupseven.hunthub.persistence.jpa.models.POJpa;

import java.util.UUID;

public interface POJpaRepository extends JpaRepository<POJpa, UUID> {
}