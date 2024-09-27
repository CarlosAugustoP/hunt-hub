package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.PoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PoRepositoryImpl implements PoRepository {

    private final Map<UUID, Hunter> hunterStorage = new HashMap<>();

    @Override
    public void save(Hunter hunter) {
        if (hunter != null && hunter.getId() != null) {
            hunterStorage.put(hunter.getId(), hunter);
        } else {
            throw new IllegalArgumentException("The Hunter´s ID can not be null.");
        }
    }

    public Hunter findById(UUID id) {
        return hunterStorage.get(id);
    }
}