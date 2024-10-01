package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.PoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class HunterService {

    private final HunterRepository hunterRepository;
    private final PoRepository poRepository;

    public HunterService(HunterRepository hunterRepository
    , PoRepository poRepository) {
        this.hunterRepository = hunterRepository;
        this.poRepository = poRepository;
    }

    public List<Hunter> getAllHunters() {
        return hunterRepository.findAll();
    }

    public Hunter findHunterById(UUID id) {
        return hunterRepository.findById(id);
    }

    public void rateHunter(Hunter evaluator, Hunter target, int rating) {
        if (evaluator.equals(target)) {
            throw new IllegalArgumentException("A avaliação não pode ser feita a si mesmo.");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5.");
        }
        target.addRating(rating);
        hunterRepository.save(target);
    }

    public void ratePO(PO po, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5");
        }
        po.addRating(rating);
        poRepository.save(po);

    }
}
