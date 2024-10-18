package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.NotificationRepository;

import java.util.*;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final List<Notification> notificationStorage = new ArrayList<>();

    @Override
    public void save(Notification notification) {
        notificationStorage.add(notification);
    }

    @Override
    public List<Notification> list(User user) {
        List<Notification> notifications = new ArrayList<>();
        notificationStorage.forEach(notification -> {
            if (notification.getUser().equals(user)) {
                notifications.add(notification);
            }
        });
        return notifications;
    }
}
