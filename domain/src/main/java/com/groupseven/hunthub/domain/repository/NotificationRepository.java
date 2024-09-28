package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;

import java.util.List;

public interface NotificationRepository {
    public void save(Notification notification);

    List<Notification> list(User user);
}
