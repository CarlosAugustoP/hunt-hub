package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserJpa toEntity(User user) {
    UserJpa userJpa = new UserJpa();
    userJpa.id = user.getId();
    userJpa.cpf = user.getCpf();
    userJpa.points = user.getPoints();
    userJpa.name = user.getName();
    userJpa.email = user.getEmail();
    userJpa.password = user.getPassword();
    return userJpa;
  }

  public User toDomain(UserJpa userJpa) {
    return new User(
        userJpa.password,
        userJpa.name,
        userJpa.email,
        userJpa.cpf);
  }
}