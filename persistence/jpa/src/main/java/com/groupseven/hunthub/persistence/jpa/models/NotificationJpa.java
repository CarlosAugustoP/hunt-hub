package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "NOTIFICATION")
public class NotificationJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String theme;
  private String message;

  private LocalDate createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserJpa user;

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public UserJpa getUser() {
    return user;
  }

  public void setUser(UserJpa user) {
    this.user = user;
  }
}