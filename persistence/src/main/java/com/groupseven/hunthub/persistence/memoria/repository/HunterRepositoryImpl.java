package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.HunterRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HunterRepositoryImpl implements HunterRepository {

    private final Map<UUID, Hunter> hunterStorage = new HashMap<>();

    public void save(Hunter hunter){
        if(hunter != null && hunter.getId() != null){
            hunterStorage.put(hunter.getId(), hunter);
        } else {
            throw new IllegalArgumentException("The PO´s ID can not be null");
        }

    }
    public Hunter findById(UUID id){
        return hunterStorage.get(id);
    }
}
