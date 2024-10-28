package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private HunterMapper hunterMapper;

  @Autowired
  private POMapper poMapper;

  public NotificationJpa toEntity(Notification notification) {
    NotificationJpa notificationJpa = new NotificationJpa();
    notificationJpa.setTheme(notification.getTheme());
    notificationJpa.setMessage(notification.getMessage());
    notificationJpa.setCreatedAt(notification.getCreatedAt());
    notificationJpa.setHunter(hunterMapper.toEntity(notification.getHunter()));
    notificationJpa.setPo(poMapper.toEntity(notification.getPo()));
    /*
     * TODO: NotificationJpa.setHunter = null SE Notification PARA PO
     *  NotificationJpa.setPo = null SE Notification PARA Hunter
     * */
    return notificationJpa;
  }

  public Notification toDomain(NotificationJpa notificationJpa) {
    Hunter hunter = null;
    PO po = null;

    if (notificationJpa.getHunter() != null) {
      hunter = hunterMapper.toDomain(notificationJpa.getHunter());
    }

    if (notificationJpa.getPo() != null) {
      po = poMapper.toDomain(notificationJpa.getPo());
    }

    return new Notification(
            notificationJpa.getMessage(),
            notificationJpa.getTheme(),
            hunter,
            po
    );
  }
}