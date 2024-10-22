package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.models.UserId;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserJpa toEntity(User user) {
    UserJpa userJpa = new UserJpa();

    if (user.getId() != null) {
      userJpa.setId(user.getId().getId());
    }

    userJpa.setCpf(user.getCpf());
    userJpa.setPoints(user.getPoints());
    userJpa.setName(user.getName());
    userJpa.setEmail(user.getEmail());
    userJpa.setPassword(user.getPassword());
    return userJpa;
  }


  public User toDomain(UserJpa userJpa) {
    UserId newUserId = new UserId(userJpa.getId());
    return new User(
        userJpa.getName(),
        userJpa.getEmail(),
        userJpa.getPassword(),
        userJpa.getCpf(),
        newUserId
    );
  }
}