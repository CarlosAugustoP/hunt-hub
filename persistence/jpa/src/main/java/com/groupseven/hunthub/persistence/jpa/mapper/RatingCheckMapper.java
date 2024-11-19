package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.RatingCheck;
import com.groupseven.hunthub.persistence.jpa.models.RatingCheckJpa;
import com.groupseven.hunthub.domain.models.UserId;
import com.groupseven.hunthub.domain.models.TaskId;
import org.springframework.stereotype.Component;

@Component
public class RatingCheckMapper {

    public RatingCheckJpa toEntity(RatingCheck domain) {
        return new RatingCheckJpa(
                domain.getHunterId().getId(),
                domain.getTaskId().getId(),
                domain.getPoId().getId()
        );
    }

    public RatingCheck toDomain(RatingCheckJpa jpa) {
        return new RatingCheck(
                new UserId(jpa.getHunterId()),
                new TaskId(jpa.getTaskId()),
                new UserId(jpa.getPoId())
        );
    }
}