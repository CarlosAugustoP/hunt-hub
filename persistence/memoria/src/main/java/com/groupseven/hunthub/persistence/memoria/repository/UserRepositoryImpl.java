package com.groupseven.hunthub.persistence.memoria.repository;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public void save(User user) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public User findById(UUID id) {
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<User> findAll() {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public void delete(UUID id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public User findByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
  }

}
