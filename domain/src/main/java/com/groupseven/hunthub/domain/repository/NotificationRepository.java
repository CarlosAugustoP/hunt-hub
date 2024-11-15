package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;

import java.util.List;
import com.groupseven.hunthub.domain.models.PO;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository {
    public void save(Notification notification);
    public List<Notification> listHunter(Hunter hunter);
    public List<Notification> listPO(PO po);
}
