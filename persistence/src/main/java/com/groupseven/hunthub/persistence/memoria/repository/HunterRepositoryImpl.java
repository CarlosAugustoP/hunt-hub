package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.HunterRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HunterRepositoryImpl implements HunterRepository {

    private final Map<UUID, PO> poStorage = new HashMap<>();

    public void save(PO po){
        if(po != null && po.getId() != null){
            poStorage.put(po.getId(), po);
        } else {
            throw new IllegalArgumentException("The PO´s ID can not be null");
        }

    }
    public PO findById(UUID id){
        return poStorage.get(id);
    }
}
