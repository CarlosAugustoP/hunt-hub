package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.PoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PoRepositoryImpl implements PoRepository {

    private final Map<UUID, PO> PoStorage = new HashMap<>();

    @Override
    public void save(PO po) {
        if (po != null && po.getId() != null) {
            PoStorage.put(po.getId(), po);
        } else {
            throw new IllegalArgumentException("The Hunter´s ID can not be null.");
        }
    }

    public PO findById(UUID id) {
        return PoStorage.get(id);
    }
}
