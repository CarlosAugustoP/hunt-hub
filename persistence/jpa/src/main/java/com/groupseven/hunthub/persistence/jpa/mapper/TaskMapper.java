package com.groupseven.hunthub.persistence.jpa.mapper;
import java.util.stream.Collectors;
import java.util.List;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;
import com.groupseven.hunthub.persistence.jpa.models.TaskStatus;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskJpa toEntity(Task task) {
        TaskJpa taskJpa = new TaskJpa();
        taskJpa.setId(task.getId());
        taskJpa.setDescription(task.getDescription());
        taskJpa.setStatus(TaskStatus.valueOf(task.getStatus()));
        taskJpa.setDeadline(task.getDeadline());
        taskJpa.setReward(task.getReward());
        taskJpa.setNumberOfMeetings(task.getNumberOfMeetings());
        taskJpa.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
        taskJpa.setRatingRequired(task.getRatingRequired());
        taskJpa.setCompleted(task.isCompleted());
        List<com.groupseven.hunthub.persistence.jpa.models.Tags> tagsJpa = task.getTags().stream()
                .map(tag -> com.groupseven.hunthub.persistence.jpa.models.Tags.valueOf(tag.name()))
                .collect(Collectors.toList());
        taskJpa.setTags(tagsJpa);
        return taskJpa;
    }

    public Task toDomain(TaskJpa taskJpa) {
        Task task = new Task();
        task.setId(taskJpa.getId());
        task.setDescription(taskJpa.getDescription());
        task.setStatus(taskJpa.getStatus().name());
        task.setDeadline(taskJpa.getDeadline());
        task.setReward(taskJpa.getReward());
        task.setNumberOfMeetings(taskJpa.getNumberOfMeetings());
        task.setNumberOfHuntersRequired(taskJpa.getNumberOfHuntersRequired());
        task.setRatingRequired(taskJpa.getRatingRequired());
        task.setCompleted(taskJpa.isCompleted());
        List<com.groupseven.hunthub.domain.models.Tags> tagsDomain = taskJpa.getTags().stream()
                .map(tag -> com.groupseven.hunthub.domain.models.Tags.valueOf(tag.name()))
                .collect(Collectors.toList());
        task.setTags(tagsDomain);
        return task;
    }
}
