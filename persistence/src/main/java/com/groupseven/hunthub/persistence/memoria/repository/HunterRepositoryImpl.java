package com.groupseven.hunthub.persistence.memoria.repository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.HunterRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

public class HunterRepositoryImpl implements HunterRepository {

    private final Map<UUID, Hunter> hunterStorage = new HashMap<>();

    @Override
    public void save(Hunter hunter) {
        if (hunter != null && hunter.getId() != null) {
            hunterStorage.put(hunter.getId(), hunter);
        } else {
            throw new IllegalArgumentException("The PO´s ID can not be null");
        }
    }

    @Override
    public Hunter findByName(String name) {
        return hunterStorage.get(name);
    }

    @Override
    public void delete(Hunter hunter) {
        hunterStorage.remove(hunter.getId());
    }

    @Override
    public List<Hunter> findAll() {
        return new ArrayList<>(hunterStorage.values());
    }

    @Override
    public Hunter findById(UUID id){
        return hunterStorage.get(id);
    }

    @Override
    public Hunter deleteById(UUID id){
        return hunterStorage.delete(id);
    }
}
