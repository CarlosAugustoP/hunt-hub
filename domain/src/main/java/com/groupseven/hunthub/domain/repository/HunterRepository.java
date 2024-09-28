package com.groupseven.hunthub.domain.repository;
import com.groupseven.hunthub.domain.models.Hunter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HunterRepository  {

    public Hunter findById(UUID id);
    Hunter findByName(String name);
    public void save(Hunter hunter);
    public void delete(Hunter hunter);
    public List<Hunter> findAll();
    
}
