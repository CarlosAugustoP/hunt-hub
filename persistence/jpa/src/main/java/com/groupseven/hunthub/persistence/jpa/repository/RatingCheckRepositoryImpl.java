package com.groupseven.hunthub.persistence.jpa.repository;

import com.groupseven.hunthub.domain.models.RatingCheck;
import com.groupseven.hunthub.domain.repository.RatingCheckRepository;
import com.groupseven.hunthub.persistence.jpa.models.RatingCheckJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.groupseven.hunthub.persistence.jpa.mapper.RatingCheckMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RatingCheckRepositoryImpl implements RatingCheckRepository {

    private @Autowired RatingCheckJpaRepository ratingCheckJpaRepository;
    private @Autowired RatingCheckMapper ratingCheckMapper;

    public void save(RatingCheck ratingCheck) {
        RatingCheckJpa ratingCheckJpa = ratingCheckMapper.toEntity(ratingCheck);
        ratingCheckJpaRepository.save(ratingCheckJpa);
    }

    public RatingCheck findById(UUID id) {
        return ratingCheckJpaRepository.findById(id)
            .map(ratingCheckMapper::toDomain)
            .orElse(null);
    }

    public List<RatingCheck> getRatingChecksByTaskId(UUID taskId) {
        return ratingCheckJpaRepository.findByTaskId(taskId)
                .stream()
                .map(ratingCheckMapper::toDomain)
                .collect(Collectors.toList());
    }



}
