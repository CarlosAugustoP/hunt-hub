package com.groupseven.hunthub.domain.repository;

import com.groupseven.hunthub.domain.models.RatingCheck;

import java.util.List;
import java.util.UUID;


public interface RatingCheckRepository {
    public void save(RatingCheck ratingCheck);
    RatingCheck findById(UUID id);
    List<RatingCheck> getRatingChecksByTaskId(UUID taskId);
}
