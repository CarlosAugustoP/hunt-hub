package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.NotificationRepository;

import java.util.*;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final List<Notification> notificationStorage = new ArrayList<>();

    @Override
    public void save(Notification notification) {
        notificationStorage.add(notification);
    }

    @Override
    public List<Notification> listHunter(Hunter hunter) {
        List<Notification> notifications = new ArrayList<>();
        notificationStorage.forEach(notification -> {
            if (notification.getHunter().equals(hunter)) {
                notifications.add(notification);
            }
        });
        return notifications;
    }

    @Override
    public List<Notification> listPO(PO po) {
        List<Notification> notifications = new ArrayList<>();
        notificationStorage.forEach(notification -> {
            if (notification.getPo().equals(po)) {
                notifications.add(notification);
            }
        });
        return notifications;
    }
}
