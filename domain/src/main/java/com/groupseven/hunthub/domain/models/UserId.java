package com.groupseven.hunthub.domain.models;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private final UUID id;

    public UserId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return id.equals(userId.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }

}
