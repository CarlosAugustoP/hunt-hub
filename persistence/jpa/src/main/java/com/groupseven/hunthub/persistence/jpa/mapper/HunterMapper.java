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

  // No need for UserMapper since we'll map the fields directly

  public HunterJpa toEntity(Hunter hunter) {
    HunterJpa hunterJpa = new HunterJpa();

    if (hunter.getId() != null) {
      hunterJpa.setId(hunter.getId().getId());
    }
    hunterJpa.setName(hunter.getName());
    hunterJpa.setEmail(hunter.getEmail());
    hunterJpa.setCpf(hunter.getCpf());
    hunterJpa.setPassword(hunter.getPassword());
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
    // Create Hunter object, which extends User
    Hunter hunter = new Hunter();

    hunter.setId(hunterJpa.getId());
    hunter.setName(hunterJpa.getName());
    hunter.setEmail(hunterJpa.getEmail());
    hunter.setCpf(hunterJpa.getCpf());
    hunter.setPassword(hunterJpa.getPassword());
    // Set other inherited fields if necessary

    // Set Hunter-specific fields
    hunter.setLinkPortfolio(hunterJpa.getLinkPortfolio());
    hunter.setTasks(hunterJpa.getTasks().stream().map(taskMapper::toDomain).collect(Collectors.toList()));
    hunter.setBio(hunterJpa.getBio());
    hunter.setProfilePicture(hunterJpa.getProfilePicture());
    hunter.setRating(hunterJpa.getRating());
    hunter.setLevel(hunterJpa.getLevel());
    hunter.setTotalRating(hunterJpa.getTotalRating());
    hunter.setRatingCount(hunterJpa.getRatingCount());
    hunter.setCertifications(hunterJpa.getCertifications());
    hunter.setLinks(hunterJpa.getLinks());
    hunter.setAchievements(
            hunterJpa.getAchievements().stream().map(achievementMapper::toDomain).collect(Collectors.toList()));
    hunter.setProjects(hunterJpa.getProjects().stream().map(projectMapper::toDomain).collect(Collectors.toList()));

    return hunter;
  }
}
