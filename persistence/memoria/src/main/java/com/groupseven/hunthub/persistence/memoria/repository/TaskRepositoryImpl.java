package com.groupseven.hunthub.persistence.memoria.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.domain.repository.TaskRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

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
        if (task != null) {
            // Verifica se o hunter já está associado à task
            if (task.getHunters().stream().anyMatch(h -> h.getId().equals(hunter.getId()))) {
                throw new IllegalStateException("Hunter has already applied to this task.");
            }
            task.applyHunter(hunter);
            taskStorage.put(taskId, task);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }



    @Override
    public void acceptHunter(UUID taskId, Hunter hunter) {
        Task task = taskStorage.get(taskId);

        if (task != null) {
            task.assignHunter(hunter);
            taskStorage.put(taskId, task);
        }

        else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }

    @Override
    public void declineHunter(UUID taskId, Hunter hunter) {
        Task task = taskStorage.get(taskId);

        if (task != null) {
            task.refuseHunter(hunter);
            taskStorage.put(taskId, task);
        }

        else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }

    @Override
    public List<Task> findTasksNotAppliedByHunter(UUID hunterId) {
        List<Task> tasksNotApplied = new ArrayList<>();

        for (Task task : taskStorage.values()) {
            // Verifica se o hunter ainda não aplicou e se a task está pendente
            boolean hasNotApplied = task.getHuntersApplied().stream()
                                        .noneMatch(hunter -> hunter.getId().getId().equals(hunterId));
            if (hasNotApplied && task.getStatus() == TaskStatus.PENDING) {
                tasksNotApplied.add(task);
            }
        }

        return tasksNotApplied;
    }

    @Override
    public List<Task> findTasksByPoId(UUID poId) {
        List<Task> tasksByPo = new ArrayList<>();
        for (Task task : taskStorage.values()) {
            if (task.getPo().getId().getId().equals(poId)) {
                tasksByPo.add(task);
            }
        }
        return tasksByPo;
    }

    @Override
    public List<Task> findTasksByHunterId(UUID hunterId) {
        List<Task> tasksByHunter = new ArrayList<>();
        for (Task task : taskStorage.values()) {
            boolean isHunterInTask = task.getHunters().stream()
                    .anyMatch(hunter -> hunter.getId().getId().equals(hunterId));
            if (isHunterInTask) {
                tasksByHunter.add(task);
            }
        }
        return tasksByHunter;
    }

    @Override
    public List<Hunter> findHuntersAppliedByTaskId(UUID taskId) {
        Task task = taskStorage.get(taskId);

        if (task == null) {
            throw new IllegalArgumentException("Task not found with ID: " + taskId);
        }

        return new ArrayList<>(task.getHuntersApplied());
    }


}