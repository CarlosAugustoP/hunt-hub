package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notification")
public class NotificationJpa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String theme;
  private String message;
  private LocalDate createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hunter_id", nullable = true)
  private HunterJpa hunter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "po_id", nullable = true)
  private POJpa po;

  public NotificationJpa() {
    // Construtor vazio para JPA
  }

  public NotificationJpa(String theme, String message, HunterJpa hunter, POJpa po) {
    if (hunter == null && po == null) {
      throw new IllegalArgumentException("A notificação precisa de pelo menos um Hunter ou PO.");
    }

    this.theme = theme;
    this.message = message;
    this.hunter = hunter;
    this.po = po;
    this.createdAt = LocalDate.now();
  }

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

  public HunterJpa getHunter() {
    return hunter;
  }

  public void setHunter(HunterJpa hunter) {
    this.hunter = hunter;
  }

  public POJpa getPo() {
    return po;
  }

  public void setPo(POJpa po) {
    this.po = po;
  }
}
