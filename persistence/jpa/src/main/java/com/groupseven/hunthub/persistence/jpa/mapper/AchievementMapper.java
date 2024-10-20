package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Achievement;
import com.groupseven.hunthub.persistence.jpa.models.AchievementJpa;

import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {

    public AchievementJpa toEntity(Achievement achievement) {
        AchievementJpa achievementJpa = new AchievementJpa();
        achievementJpa.setName(achievement.getName());
        achievementJpa.setDescription(achievement.getDescription());
        achievementJpa.setIcon(achievement.getIcon());
        return achievementJpa;
    }

    public Achievement toDomain(AchievementJpa achievementJpa) {
        return new Achievement(
                achievementJpa.getName(),
                achievementJpa.getDescription(),
                achievementJpa.getIcon());
    }
}