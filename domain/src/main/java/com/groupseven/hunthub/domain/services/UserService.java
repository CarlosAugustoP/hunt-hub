// UserService.java
package com.groupseven.hunthub.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = findUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return user;
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
