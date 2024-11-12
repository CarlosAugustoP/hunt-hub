package com.groupseven.hunthub.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.groupseven.hunthub.domain.repository.PoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private TaskRepository taskRepository;

    @Autowired
    private PoRepository poRepository;

    @Autowired
    private HunterRepository hunterRepository;

    public TaskService(TaskRepository taskRepository, PoRepository poRepository) {
        this.poRepository = poRepository;
        this.taskRepository = taskRepository;
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

        // Deduz os pontos necessários para criar a tarefa
        po.setPoints(po.getPoints() - reward);
        poRepository.save(po);

        // Crie a task
        TaskId taskId = new TaskId(UUID.randomUUID());
        Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired,ratingRequired, tags, taskId);

        // Associe o PO à task
        task.setPo(po);
        List<UUID> hunterIds = new ArrayList<>();
        List<UUID> hunterAppliedIds = new ArrayList<>();

        System.out.println("estou aqui createTask service");

        // Salve a entidade JPA
        taskRepository.save(task);

        // Retorne a task criada
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

    public int getElements(UUID taskId, UUID hunterId) {
        Task foundTask = taskRepository.findById(taskId);
        Hunter foundHunter = hunterRepository.findById(hunterId);

        if (foundTask == null) {
            System.out.println("The task with ID " + taskId + " doesn't exist.");
            return 0;
        } else if (foundHunter == null) {
            System.out.println("The hunter with ID " + hunterId + " doesn't exist.");
            return 0;
        } else {
            applyHunterToTask(foundTask, foundHunter);
            return 1;
        }
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
        } else if (task.getStatus().equals(TaskStatus.PENDING)) {
            throw new IllegalStateException("Cannot apply to task. Rating required: " + task.getRatingRequired()
                    + ". Your rating " + hunter.getRating());
        } else if (task.getStatus().equals(TaskStatus.DONE) || task.getStatus().equals(TaskStatus.CANCELED)) {
            throw new IllegalStateException("Cannot apply to task. The task is already closed.");
        }
    }


    public void acceptHunter(Task task, Hunter hunter) {
        task.assignHunter(hunter);
        taskRepository.acceptHunter(task.getId().getId(), hunter);
    }

    public void declineHunter(Task task, Hunter hunter) {
        task.refuseHunter(hunter);
        taskRepository.declineHunter(task.getId().getId(), hunter);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
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

        return existingTask;
    }

}
