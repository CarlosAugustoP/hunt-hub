package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.NotificationRepository;

@Entity
@Table(name = "NOTIFICATION")
public class NotificationJpa {
  private UserJpa user;
  private String theme;
  private String message;
  private LocalDate createdAt;
}

interface NotificationJpaRepository extends JpaRepository<NotificationJpa, UUID> {
}

@Repository
class NotificationRepositoryImpl implements NotificationRepository {
  @Autowired
  NotificationJpaRepository repository;

  @Override
  public void save(Notification notification) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public List<Notification> list(User user) {
    throw new UnsupportedOperationException("Unimplemented method 'list'");
  }
}
