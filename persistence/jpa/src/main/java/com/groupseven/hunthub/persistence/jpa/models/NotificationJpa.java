package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "notification")
public class NotificationJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String theme;
  private String message;
  private LocalDate createdAt;

  @Column(name = "hunter_id", nullable = true)
  private UUID hunterId;

  @Column(name = "po_id", nullable = true)
  private UUID poId;

  public NotificationJpa() {
    // Construtor vazio para JPA
  }

  public NotificationJpa(String theme, String message, UUID hunterId, UUID poId) {
    if (hunterId == null && poId == null) {
      throw new IllegalArgumentException("A notificação precisa de pelo menos um Hunter ou PO.");
    }

    this.theme = theme;
    this.message = message;
    this.hunterId = hunterId;
    this.poId = poId;
    this.createdAt = LocalDate.now();
  }

  // Getters e Setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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

  public UUID getHunterId() {
    return hunterId;
  }

  public void setHunterId(UUID hunterId) {
    this.hunterId = hunterId;
  }

  public UUID getPoId() {
    return poId;
  }

  public void setPoId(UUID poId) {
    this.poId = poId;
  }
}
