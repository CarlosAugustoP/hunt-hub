package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.domain.models.Notification;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.NotificationRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.NotificationMapper;
import com.groupseven.hunthub.persistence.jpa.mapper.UserMapper;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

  @Autowired
  NotificationJpaRepository repository;

  @Autowired
  NotificationMapper notificationMapper;

  @Autowired
  UserMapper userMapper;

  @Override
  public void save(Notification notification) {
    NotificationJpa notificationJpa = notificationMapper.toEntity(notification);
    repository.save(notificationJpa);
  }

  @Override
  public List<Notification> listHunter(Hunter hunter) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Notification> listPO(PO po) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

//  @Override
//  public List<Notification> list(User user) {
//    UserJpa userJpa = userMapper.toEntity(user);
//    List<NotificationJpa> notifications = repository.findByUser(userJpa);
//    return notifications.stream()
//            .map(notificationMapper::toDomain)
//            .collect(Collectors.toList());
//  }

}