package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import org.springframework.stereotype.Service;

@Service
public class HunterService {

    private final HunterRepository hunterRepository;

    public HunterService(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }

    public void hunterApplyforTask(Task task, Hunter hunter) {


    }


    
}
