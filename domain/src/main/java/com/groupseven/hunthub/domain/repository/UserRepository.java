package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.User;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

  void save(User user);

  User findById(UUID id);

  List<User> findAll();

  void delete(UUID id);
}