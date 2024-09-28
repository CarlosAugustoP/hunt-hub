package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.PoRepository;
import org.springframework.stereotype.Service;

@Service
public class POService {

    private final PoRepository poRepository;

    public POService(PoRepository poRepository) {
        this.poRepository = poRepository;
    }

    public void ratePO(PO po, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5.");
        }

        po.addRating(rating);
        
        poRepository.save(po);
    }
}
