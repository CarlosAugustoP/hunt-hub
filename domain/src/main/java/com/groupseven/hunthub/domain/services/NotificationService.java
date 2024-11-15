package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean NotifyPO(PO po, String task, String message) {
        Notification notification = new Notification(message, task, po);
        notificationRepository.save(notification);
        return true;
    }

    public boolean NotifyHunter (Hunter hunter, String task, String message) {
        Notification notification = new Notification(message, task, hunter);
        notificationRepository.save(notification);
        return true;
    }

}
