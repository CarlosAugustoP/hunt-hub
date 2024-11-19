package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.RatingCheck;
import com.groupseven.hunthub.domain.repository.RatingCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RatingCheckService {
    private @Autowired RatingCheckRepository ratingCheckRepository;

    public void save(RatingCheck ratingCheck) {
        ratingCheckRepository.save(ratingCheck);
    }

    public RatingCheck findById(UUID id) {
        return ratingCheckRepository.findById(id);
    }


}
