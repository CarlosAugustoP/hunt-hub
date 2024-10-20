package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserJpa toEntity(User user) {
    UserJpa userJpa = new UserJpa();
    userJpa.setId(user.getId());
    userJpa.setCpf(user.getCpf());
    userJpa.setPoints(user.getPoints());
    userJpa.setName(user.getName());
    userJpa.setEmail(user.getEmail());
    userJpa.setPassword(user.getPassword());
    return userJpa;
  }

  public User toDomain(UserJpa userJpa) {
    return new User(
        userJpa.getPassword(),
        userJpa.getName(),
        userJpa.getEmail(),
        userJpa.getCpf());
  }
}