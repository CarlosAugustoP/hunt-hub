package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

  @Autowired
  private UserMapper userMapper;

  public NotificationJpa toEntity(Notification notification) {
    NotificationJpa notificationJpa = new NotificationJpa();
    notificationJpa.setTheme(notification.getTheme());
    notificationJpa.setMessage(notification.getMessage());
    notificationJpa.setCreatedAt(notification.getCreatedAt());
    notificationJpa.setUser(userMapper.toEntity(notification.getUser()));
    return notificationJpa;
  }

  public Notification toDomain(NotificationJpa notificationJpa) {
    Notification notification = new Notification();
    notification.setTheme(notificationJpa.getTheme());
    notification.setMessage(notificationJpa.getMessage());
    notification.setUser(userMapper.toDomain(notificationJpa.getUser()));
    return notification;
  }
}