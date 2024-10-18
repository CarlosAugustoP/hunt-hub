package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;
import com.groupseven.hunthub.persistence.jpa.models.TaskStatus;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskJpa toEntity(Task task) {
        TaskJpa taskJpa = new TaskJpa();
        taskJpa.id = task.getId();
        taskJpa.description = task.getDescription();
        taskJpa.status = TaskStatus.valueOf(task.getStatus());
        taskJpa.deadline = task.getDeadline();
        taskJpa.reward = task.getReward();
        taskJpa.numberOfMeetings = task.getNumberOfMeetings();
        taskJpa.numberOfHuntersRequired = task.getNumberOfHuntersRequired();
        taskJpa.ratingRequired = task.getRatingRequired();
        taskJpa.completed = task.isCompleted();
        return taskJpa;
    }

    public Task toDomain(TaskJpa taskJpa) {
        Task task = new Task();
        task.setId(taskJpa.id);
        task.setDescription(taskJpa.description);
        task.setStatus(taskJpa.status.name());
        task.setDeadline(taskJpa.deadline);
        task.setReward(taskJpa.reward);
        task.setNumberOfMeetings(taskJpa.numberOfMeetings);
        task.setNumberOfHuntersRequired(taskJpa.numberOfHuntersRequired);
        task.setRatingRequired(taskJpa.ratingRequired);
        task.setCompleted(taskJpa.completed);
        return task;
    }
}
