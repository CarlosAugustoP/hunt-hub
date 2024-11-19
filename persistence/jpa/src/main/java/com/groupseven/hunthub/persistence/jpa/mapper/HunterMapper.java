package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class HunterMapper {

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private AchievementMapper achievementMapper;

  @Autowired
  private ProjectMapper projectMapper;

  public HunterJpa toEntity(Hunter hunter, List<UUID> taskIds) {
    HunterJpa hunterJpa = new HunterJpa();

    if (hunter.getId() != null) {
      hunterJpa.setId(hunter.getId().getId());
    }
    hunterJpa.setPoints(hunter.getPoints());
    hunterJpa.setName(hunter.getName());
    hunterJpa.setEmail(hunter.getEmail());
    hunterJpa.setCpf(hunter.getCpf());
    hunterJpa.setPassword(hunter.getPassword());
    hunterJpa.setLinkPortfolio(hunter.getLinkPortfolio());
    hunterJpa.setTaskIds(taskIds);
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
    hunterJpa.setRole(hunter.getRole());
    return hunterJpa;
  }

  public Hunter toDomain(HunterJpa hunterJpa, List<Task> tasks) {
    Hunter hunter = new Hunter();
    hunter.setPoints(hunterJpa.getPoints());
    hunter.setId(hunterJpa.getId());
    hunter.setName(hunterJpa.getName());
    hunter.setEmail(hunterJpa.getEmail());
    hunter.setCpf(hunterJpa.getCpf());
    hunter.setPassword(hunterJpa.getPassword());
    hunter.setLinkPortfolio(hunterJpa.getLinkPortfolio());
    hunter.setTasks(tasks);
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
    hunter.setRole(hunterJpa.getRole());
    return hunter;
  }
}
