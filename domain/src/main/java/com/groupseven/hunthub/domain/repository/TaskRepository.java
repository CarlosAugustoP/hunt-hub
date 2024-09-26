package com.groupseven.hunthub.domain.repository;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.Task;

public interface TaskRepository {

    public void save(Task task);

    public Task findById(UUID id);

    public List<Task> findAll();

    public void delete(UUID id);

}
