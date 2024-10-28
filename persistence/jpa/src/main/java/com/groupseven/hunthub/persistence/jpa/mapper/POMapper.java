package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.persistence.jpa.models.POJpa;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class POMapper {

  @Autowired
  private TaskMapper taskMapper;

  public POJpa toEntity(PO po) {
    POJpa poJpa = new POJpa();
    if (po.getId() != null) {
      poJpa.setId(po.getId().getId());
    }
    poJpa.setEmail(po.getEmail());
    poJpa.setName(po.getName());
    poJpa.setCpf(po.getCpf());
    poJpa.setPassword(po.getPassword());
    poJpa.setLevels(po.getLevels());
    poJpa.setRating(po.getRating());
    poJpa.setTotalRating(po.getTotalRating());
    poJpa.setRatingCount(po.getRatingCount());
    poJpa.setProfilePicture(po.getProfilePicture());
    poJpa.setBio(po.getBio());

    List<TaskJpa> taskJpaList = po.getTasks().stream()
        .map(taskMapper::toEntity)
        .collect(Collectors.toList());
    poJpa.setTasks(taskJpaList);

    return poJpa;
  }

  public PO toDomain(POJpa poJpa) {
    PO po = new PO();
    po.setId(poJpa.getId());
    po.setName(poJpa.getName());
    po.setEmail(poJpa.getEmail());
    po.setCpf(poJpa.getCpf());
    po.setPassword(poJpa.getPassword());
    po.setLevels(poJpa.getLevels());
    po.setRating(poJpa.getRating());
    po.setTotalRating(poJpa.getTotalRating());
    po.setRatingCount(poJpa.getRatingCount());
    po.setProfilePicture(poJpa.getProfilePicture());
    po.setBio(poJpa.getBio());

    List<Task> taskList = poJpa.getTasks().stream()
        .map(taskMapper::toDomain)
        .collect(Collectors.toList());
    po.setTasks(taskList);

    return po;
  }
}