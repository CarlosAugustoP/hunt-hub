package com.groupseven.hunthub.persistence.memoria.repository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.HunterRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

public class HunterRepositoryImpl implements HunterRepository {

    private final Map<UUID, Hunter> hunters = new HashMap<>();

    @Override
    public Hunter findByName(String name) {
        return hunters.get(name);
    }

    @Override
    public void save(Hunter hunter) {
        hunters.put(hunter.getId(), hunter);
    }

    @Override
    public void delete(Hunter hunter) {
        hunters.remove(hunter.getId());

    }

    @Override
    public List<Hunter> findAll() {
        return new ArrayList<>(hunters.values());

    }

    @Override
    public Hunter findById(UUID id){
        return hunters.get(id);
    }
}
