package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.RatingCheck;
import com.groupseven.hunthub.domain.repository.RatingCheckRepository;

import java.util.List;
import java.util.UUID;

public class RatingCheckRepositoryImpl implements RatingCheckRepository {

    public void save(RatingCheck ratingCheck) {
        new RatingCheck(ratingCheck.getHunterId(), ratingCheck.getTaskId(), ratingCheck.getPoId());
    }
    public RatingCheck findById(UUID id) {
        return null;
    }
    public List<RatingCheck> getRatingChecksByTaskId(UUID taskId) {
        return null;
    }
}
