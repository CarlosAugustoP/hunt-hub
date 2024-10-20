package com.groupseven.hunthub.persistence.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.UserRepository;
import com.groupseven.hunthub.persistence.jpa.mapper.UserMapper;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @Autowired
  UserJpaRepository repository;

  @Autowired
  UserMapper userMapper;

  @Override
  public void save(User user) {
    UserJpa userJpa = userMapper.toEntity(user);
    repository.save(userJpa);
  }

  @Override
  public User findById(UUID id) {
    return repository.findById(id)
        .map(userMapper::toDomain)
        .orElse(null);
  }

  @Override
  public List<User> findAll() {
    return repository.findAll().stream()
        .map(userMapper::toDomain)
        .toList();
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}