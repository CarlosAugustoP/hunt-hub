package com.groupseven.hunthub.persistence.memoria.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.domain.services.TaskService;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private TaskService taskService;

    private final Map<UUID, Task> taskStorage = new HashMap<>();

    @Override
    public void save(Task task) {
        // System.out.println("TaskRepositoryImpl.save");
        taskStorage.put(task.getId().getId(), task);
    }

    @Override
    public Task findById(UUID id) {
        // System.out.println("TaskRepositoryImpl.findById");
        return taskStorage.get(id);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskStorage.values());
    }

    @Override
    public void delete(UUID id) {
        taskStorage.remove(id);
    }

    @Override
    public void applyHunterToTask(UUID taskId, Hunter hunter) {
        Task task = taskStorage.get(taskId);

        taskService.applyHunterToTask(task, hunter);

        taskStorage.put(taskId, task);

    }


    @Override
    public void acceptHunter(UUID taskId, Hunter hunter) {
        Task task = taskStorage.get(taskId);

        taskService.acceptHunter(task, hunter);

        taskStorage.put(taskId, task);
    }

    @Override
    public void declineHunter(UUID taskId, Hunter hunter) {
        Task task = taskStorage.get(taskId);

        taskService.declineHunter(task, hunter);

        taskStorage.put(taskId, task);
    }

}