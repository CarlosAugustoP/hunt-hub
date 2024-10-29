package com.groupseven.hunthub.persistence.jpa.mapper;
import com.groupseven.hunthub.persistence.jpa.models.TaskStatusJPA;
import com.groupseven.hunthub.domain.models.TaskStatus;

public class TaskStatusMapper {

    public static TaskStatusJPA toEntity(TaskStatus taskStatus) {
        return TaskStatusJPA.valueOf(taskStatus.name());
    }

    public static TaskStatus toDomain(TaskStatusJPA taskStatusJpa) {
        return TaskStatus.valueOf(taskStatusJpa.name());
    }
}