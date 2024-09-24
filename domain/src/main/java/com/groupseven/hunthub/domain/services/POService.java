package com.groupseven.hunthub.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.groupseven.hunthub.domain.repository.PORepository;

@Service
public class POService {
    
    private final PORepository poRepository;

    @Autowired
    public POService(PORepository poRepository) {
        this.poRepository = poRepository;
    }

    
}
