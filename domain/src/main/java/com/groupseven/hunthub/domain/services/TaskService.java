package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskService() {

    }

    public void createTask(PO po,
                           String name,
                           String description,
                           String title,
                           Date deadline,
                           int reward,
                           int numberOfMeetings,
                           int numberOfHuntersRequired,
                           double ratingRequired,
                           List<Tags> tags
    ) {
        if (po.getPoints() < numberOfHuntersRequired * reward) {
            throw new IllegalArgumentException("Not enough points");
        }
        po.setPoints(po.getPoints() - numberOfHuntersRequired * reward);

        TaskId taskId = new TaskId(UUID.randomUUID());
        Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired, tags, taskId);
        task.setPo(po);

        taskRepository.save(task);

        po.addTask(task);
    }

    public List<Task> findByFilter(Map<String, Object> filters) {
        List<Task> filteredTasks = taskRepository.findAll();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String filter = entry.getKey();
            Object value = entry.getValue();

            switch (filter) {
                case "reward" -> filteredTasks.removeIf(task -> task.getReward() < (int) value);
                case "numberOfMeetings" -> filteredTasks.removeIf(task -> task.getNumberOfMeetings() > (int) value);
                case "ratingRequired" -> filteredTasks.removeIf(task -> task.getRatingRequired() < (double) value);
                case "PORating" -> filteredTasks.removeIf(task -> task.getPo().getRating() > (int) value);
                case "numberOfHuntersRequired" -> filteredTasks.removeIf(task -> task.getNumberOfHuntersRequired() < (int) value);
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

    public static void applyHunterToTask(Task task, Hunter hunter) {
        if (hunter.getRating() >= task.getRatingRequired() && task.getStatus().equals("open")) {
            task.applyHunter(hunter);
        } else if (task.getStatus().equals("open")) {
            throw new IllegalStateException("Cannot apply to task. Rating required: " + task.getRatingRequired() + ". Your rating " + hunter.getRating());
        } else {
            throw new IllegalStateException("Cannot apply to task. The task is already closed.");
        }
    }

    public static void acceptHunter(Task task, Hunter hunter) {
        task.assignHunter(hunter);
    }
}
