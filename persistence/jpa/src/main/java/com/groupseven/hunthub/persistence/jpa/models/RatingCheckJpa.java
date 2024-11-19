package com.groupseven.hunthub.persistence.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class RatingCheckJpa {

    @Id
    private UUID id;

    private UUID hunterId;
    private UUID taskId;
    private UUID poId;

    public RatingCheckJpa() {
    }

    public RatingCheckJpa(UUID hunterId, UUID taskId, UUID poId) {
        this.id = UUID.randomUUID();
        this.hunterId = hunterId;
        this.taskId = taskId;
        this.poId = poId;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHunterId() {
        return hunterId;
    }

    public void setHunterId(UUID hunterId) {
        this.hunterId = hunterId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public UUID getPoId() {
        return poId;
    }

    public void setPoId(UUID poId) {
        this.poId = poId;
    }
}
