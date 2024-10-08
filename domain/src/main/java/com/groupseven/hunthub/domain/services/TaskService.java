package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.PO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
                           List<String> tags
    ) {
        if (po.getPoints() < numberOfHuntersRequired * reward) {
            throw new IllegalArgumentException("Not enough points");
        }
        po.setPoints(po.getPoints() - numberOfHuntersRequired * reward);

        Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired, tags);
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
                default -> {
                    // Filtro desconhecido ou não suportado
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
