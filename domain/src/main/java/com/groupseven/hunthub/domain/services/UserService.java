package com.groupseven.hunthub.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(String name, String email, String password, String cpf) {
    return new User(name, email, password, cpf, null);
  }

  public User findUserById(String id) {
    return new User();
  }

  public boolean deleteUser(UUID id) {
    userRepository.delete(id);
    if (userRepository.findById(id) != null) {
      throw new RuntimeException("User not deleted");
    }
    return true;
  }
}
