package com.groupseven.hunthub.domain.models;

import java.util.Objects;
import java.util.UUID;

public class ProjectId {
    private final UUID id;

    public ProjectId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectId projectId = (ProjectId) o;
        return id.equals(projectId.id);
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }
}
