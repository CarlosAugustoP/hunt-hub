package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationJpa, Long> {
}