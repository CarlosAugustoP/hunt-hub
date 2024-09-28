package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import org.springframework.stereotype.Service;

@Service
public class HunterService {

    private final HunterRepository hunterRepository;

    public HunterService(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }

    public void ratePO(PO po, int rating){
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException("The rating must be between 1 and 5");
        }

        po.addRating(rating);

        hunterRepository.save(po);
    }
}
