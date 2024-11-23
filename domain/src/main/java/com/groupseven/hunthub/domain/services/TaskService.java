package com.groupseven.hunthub.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.groupseven.hunthub.domain.repository.PoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskId;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.domain.repository.HunterRepository;
import com.groupseven.hunthub.domain.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PoRepository poRepository;

    @Autowired
    private HunterRepository hunterRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public TaskService(TaskRepository taskRepository, PoRepository poRepository, NotificationService notificationService) {
        this.poRepository = poRepository;
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    public TaskService() {

    }

    public Task createTask(
            UUID poId,
            String description,
            String title,
            Date deadline,
            int reward,
            int numberOfMeetings,
            int numberOfHuntersRequired,
            double ratingRequired,
            List<Tags> tags) {

        PO po = poRepository.findById(poId);
        if (po == null) {
            throw new IllegalArgumentException("PO not found with ID: " + poId);
        }

        if (po.getPoints() < reward) {
            throw new IllegalArgumentException("Not enough points");
        }

        po.setPoints(po.getPoints() - reward);
        poRepository.save(po);

        TaskId taskId = new TaskId(UUID.randomUUID());
        Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired,ratingRequired, tags, taskId);

        task.setPo(po);
        List<UUID> hunterIds = new ArrayList<>();
        List<UUID> hunterAppliedIds = new ArrayList<>();

        taskRepository.save(task);
        notificationService.notifyAllObservers("Uma nova task foi criada:"+ task.getTitle(), "New task", task);

        return task;
    }

    public List<Task> findByFilter(Map<String, Object> filters) {
        List<Task> filteredTasks = taskRepository.findAll();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String filter = entry.getKey();
            Object value = entry.getValue();

            switch (filter) {
                case "reward" ->
                    filteredTasks.removeIf(task -> task.getReward() < (int) value);
                case "numberOfMeetings" ->
                    filteredTasks.removeIf(task -> task.getNumberOfMeetings() > (int) value);
                case "ratingRequired" ->
                    filteredTasks.removeIf(task -> task.getRatingRequired() < (double) value);
                case "PORating" ->
                    filteredTasks.removeIf(task -> task.getPo().getRating() > (int) value);
                case "numberOfHuntersRequired" ->
                    filteredTasks.removeIf(task -> task.getNumberOfHuntersRequired() < (int) value);
                case "tags" -> {
                    List<Tags> tags;

                    if (value instanceof List<?> tagList && !tagList.isEmpty() && tagList.get(0) instanceof String) {
                        tags = new ArrayList<>();
                        for (String tagString : (List<String>) value) {
                            try {
                                Tags tag = Tags.valueOf(tagString.trim().toUpperCase());
                                tags.add(tag);
                            } catch (IllegalArgumentException e) {
                                System.err.println("Tag inválida: " + tagString.trim());
                            }
                        }
                    } else {
                        tags = (List<Tags>) value;
                    }

                    filteredTasks.removeIf(task -> !new HashSet<>(task.getTags()).containsAll(tags));
                }
                default -> {
                }
            }
        }
        return filteredTasks;
    }

    public Map<String, Object> parseFilters(Map<String, String> filters) {
        return filters.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            switch (key) {
                                case "reward", "numberOfMeetings", "PORating", "numberOfHuntersRequired":
                                    return Integer.parseInt(value);
                                case "ratingRequired":
                                    return Double.parseDouble(value);
                                case "tags":
                                    return List.of(value.split(","));
                                default:
                                    return value;
                            }
                        }
                ));
    }

    public Task getTask(UUID taskId) {

        Task foundTask = taskRepository.findById(taskId);

        if (foundTask == null) {
            System.out.println("The task with ID " + taskId + " doesn't exist.");
        }

        return foundTask;
    }

    public Hunter getHunter(UUID hunterId) {
        Hunter foundHunter = hunterRepository.findById(hunterId);

        if (foundHunter == null) {
            System.out.println("The hunter with ID " + hunterId + " doesn't exist.");
        }

        return foundHunter;
    }

    public void applyHunterToTask(Task task, Hunter hunter) {
        if (hunter.getRating() >= task.getRatingRequired() && task.getStatus().equals(TaskStatus.PENDING)) {
            task.applyHunter(hunter);
            taskRepository.applyHunterToTask(task.getId().getId(), hunter);
        }

        else if (task.getStatus().equals(TaskStatus.PENDING)) {
            throw new IllegalStateException("Cannot apply to task. Rating required: " + task.getRatingRequired()
                    + ". Your rating " + hunter.getRating());
        }

        else if (task.getStatus().equals(TaskStatus.DONE) || task.getStatus().equals(TaskStatus.CANCELED)) {
            throw new IllegalStateException("Cannot apply to task. The task is already closed.");
        }

        notificationService.notifyAllObservers("O hunter " + hunter.getName() + " aplicou para a task " + task.getTitle(), "Hunter applied", task);
        notificationService.NotifyHunter(hunter, "Task applied", "Você aplicou para a task " + task.getTitle());
    }

    public boolean hasHunterApplied(Task task, Hunter hunter) {
        return task.getHunters().stream()
                   .anyMatch(h -> h.getId().equals(hunter.getId()));
    }
    

    public void acceptHunter(Task task, Hunter hunter) {
        task.assignHunter(hunter);
        notificationService.notifyAllObservers("O hunter " + hunter.getName() + " foi aceito a task: " + task.getTitle() + "Bom trabalho, " + hunter.getName() + "!", "Hunter accepted", task);
        notificationService.NotifyHunter(hunter, "Task accepted", "Você foi aceito para a task " + task.getTitle()+"! Parabéns, " + hunter.getName() + "!");
        taskRepository.acceptHunter(task.getId().getId(), hunter);
    }

    public void declineHunter(Task task, Hunter hunter) {
        task.refuseHunter(hunter);
        taskRepository.declineHunter(task.getId().getId(), hunter);
    }

    public List<Task> getAll() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.getStatus().equals(TaskStatus.DONE))
                .toList();
    }

    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId);
    }

    public List<Task> getTasksNotAppliedByHunter(UUID hunterId) {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                       .filter(task -> !task.getHuntersApplied().stream()
                                            .anyMatch(hunter -> hunter.getId().getId().equals(hunterId))
                               && task.getStatus().equals(TaskStatus.PENDING))
                       .toList();
    }
    

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        taskRepository.delete(id);
    }

    @Transactional
    public Task updateTask(UUID id, Task updatedTaskData) {
        Task existingTask = taskRepository.findById(id);

        if (existingTask == null) {
            throw new IllegalArgumentException("Task with ID " + id + " not found.");
        }

        updatedTaskData.setId(existingTask.getId());
        updatedTaskData.setPo(existingTask.getPo());
        updatedTaskData.setDeadline(existingTask.getDeadline());
        updatedTaskData.setStatus(existingTask.getStatus());

        if (updatedTaskData.getDescription() != null) {
            existingTask.setDescription(updatedTaskData.getDescription());
        }

        if (updatedTaskData.getTitle() != null) {
            existingTask.setTitle(updatedTaskData.getTitle());
        }

        if (updatedTaskData.getReward() > 0) {
            existingTask.setReward(updatedTaskData.getReward());
        }

        if (updatedTaskData.getNumberOfMeetings() > 0) {
            existingTask.setNumberOfMeetings(updatedTaskData.getNumberOfMeetings());
        }

        if (updatedTaskData.getNumberOfHuntersRequired() > 0) {
            existingTask.setNumberOfHuntersRequired(updatedTaskData.getNumberOfHuntersRequired());
        }

        if (updatedTaskData.getTags() != null && !updatedTaskData.getTags().isEmpty()) {
            existingTask.setTags(updatedTaskData.getTags());
        }

        if (updatedTaskData.getRatingRequired() > 0) {
            existingTask.setRatingRequired(updatedTaskData.getRatingRequired());
        }

        taskRepository.save(existingTask);
        notificationService.notifyAllObservers("A task " + existingTask.getTitle() + " foi atualizada", "Task updated", existingTask);

        return existingTask;
    }

    public void completeTask(Task task) {
        task.complete();
        taskRepository.save(task);
        notificationService.notifyAllObservers("Parabéns pelo trabalho! A task " + task.getTitle() + " foi completada", "Task completed", task);
    }


    public void save(Task task) {
        taskRepository.save(task);
    }
}
