package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupseven.hunthub.persistence.jpa.models.POJpa;

public interface POJpaRepository extends JpaRepository<POJpa, Long> {
}