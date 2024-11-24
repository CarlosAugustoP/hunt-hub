package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;

import java.util.List;
import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationJpa, UUID> {
    List<NotificationJpa> findByHunterId(UUID hunterId);
    List<NotificationJpa> findByPoId(UUID poId);
}