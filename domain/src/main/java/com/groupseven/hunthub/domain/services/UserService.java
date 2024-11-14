// UserService.java
package com.groupseven.hunthub.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.repository.UserRepository;

import java.util.List;
import java.util.UUID;

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

  // Método para criar o usuário com base no role passado no body
  public void createUser(User user, String role) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Define o role e instancia o tipo correto
    if ("HUNTER".equalsIgnoreCase(role)) {
      Hunter hunter = new Hunter();
      hunter.setRole("ROLE_HUNTER");
      copyUserData(user, hunter);
      userRepository.save(hunter);
    } else if ("PO".equalsIgnoreCase(role)) {
      PO po = new PO();
      po.setRole("ROLE_PO");
      copyUserData(user, po);
      userRepository.save(po);
    } else {
      throw new IllegalArgumentException("Invalid role specified");
    }
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

  public User findUserById(UUID id) {
    return userRepository.findById(id);
  }

  // Método auxiliar para copiar dados de User para uma subclasse
  private void copyUserData(User source, User target) {
    target.setName(source.getName());
    target.setEmail(source.getEmail());
    target.setCpf(source.getCpf());
    target.setPassword(source.getPassword());
    target.setPoints(source.getPoints());
  }
}
