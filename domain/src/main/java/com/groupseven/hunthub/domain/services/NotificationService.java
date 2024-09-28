package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;

import java.time.LocalDate;

public class NotificationService {
    public void Notify(User user, String task, String message) {
        Notification notification = new Notification(message, task, user);
        NotificationRepository.add(notification);
    }
}
