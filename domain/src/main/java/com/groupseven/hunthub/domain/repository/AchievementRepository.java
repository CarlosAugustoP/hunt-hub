package com.groupseven.hunthub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.groupseven.hunthub.domain.models.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    
    
}
