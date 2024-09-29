package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.NotificationRepository;

public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean Notify(User user, String task, String message) {
        Notification notification = new Notification(message, task, user);
        notificationRepository.save(notification);
        return true;
    }
}
