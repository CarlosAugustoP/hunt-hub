package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HunterMapper {

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private AchievementMapper achievementMapper;

  @Autowired
  private ProjectMapper projectMapper;

  public HunterJpa toEntity(Hunter hunter) {
    HunterJpa hunterJpa = new HunterJpa();
    hunterJpa.setLinkPortfolio(hunter.getLinkPortfolio());
    hunterJpa.setTasks(hunter.getTasks().stream().map(taskMapper::toEntity).collect(Collectors.toList()));
    hunterJpa.setBio(hunter.getBio());
    hunterJpa.setProfilePicture(hunter.getProfilePicture());
    hunterJpa.setRating(hunter.getRating());
    hunterJpa.setLevel(hunter.getLevel());
    hunterJpa.setTotalRating(hunter.getTotalRating());
    hunterJpa.setRatingCount(hunter.getRatingCount());
    hunterJpa.setCertifications(hunter.getCertifications());
    hunterJpa.setLinks(hunter.getLinks());
    hunterJpa.setAchievements(
        hunter.getAchievements().stream().map(achievementMapper::toEntity).collect(Collectors.toList()));
    hunterJpa.setProjects(hunter.getProjects().stream().map(projectMapper::toEntity).collect(Collectors.toList()));
    return hunterJpa;
  }

  public Hunter toDomain(HunterJpa hunterJpa) {
    return new Hunter(
        hunterJpa.getCpf(),
        hunterJpa.getName(),
        hunterJpa.getEmail(),
        hunterJpa.getPassword(),
        hunterJpa.getLinkPortfolio(),
        hunterJpa.getTasks().stream().map(taskMapper::toDomain).collect(Collectors.toList()),
        hunterJpa.getBio(),
        hunterJpa.getProfilePicture(),
        hunterJpa.getCertifications(),
        hunterJpa.getLinks(),
        hunterJpa.getAchievements().stream().map(achievementMapper::toDomain).collect(Collectors.toList()),
        hunterJpa.getProjects().stream().map(projectMapper::toDomain).collect(Collectors.toList()));
  }
}