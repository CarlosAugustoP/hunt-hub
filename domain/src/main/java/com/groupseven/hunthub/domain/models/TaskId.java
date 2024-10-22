package com.groupseven.hunthub.domain.models;

import java.util.Objects;
import java.util.UUID;

public class TaskId {
    private final UUID id;

    public TaskId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId = (TaskId) o;
        return id.equals(taskId.id);
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }
}
