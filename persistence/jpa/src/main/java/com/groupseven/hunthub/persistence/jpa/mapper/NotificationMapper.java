package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

  public NotificationJpa toEntity(Notification notification) {
    NotificationJpa notificationJpa = new NotificationJpa();
    notificationJpa.setTheme(notification.getTheme());
    notificationJpa.setMessage(notification.getMessage());
    notificationJpa.setCreatedAt(notification.getCreatedAt());

    if (notification.getHunter() != null && notification.getHunter().getId() != null) {
      notificationJpa.setHunterId(notification.getHunter().getId().getId());
    }

    if (notification.getPo() != null && notification.getPo().getId() != null) {
      notificationJpa.setPoId(notification.getPo().getId().getId());
    }

    return notificationJpa;
  }

  public Notification toDomain(NotificationJpa notificationJpa, Hunter hunter, PO po) {
    return new Notification(
            notificationJpa.getMessage(),
            notificationJpa.getTheme(),
            hunter,
            po
    );
  }
}
