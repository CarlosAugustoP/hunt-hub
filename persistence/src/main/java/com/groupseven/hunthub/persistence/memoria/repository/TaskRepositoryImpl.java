package com.groupseven.hunthub.persistence.memoria.repository;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final Map<UUID, Task> taskStorage = new HashMap<>();

    @Override
    public void save(Task task) {
        System.out.println("TaskRepositoryImpl.save");
        taskStorage.put(task.getId(), task);
    }

    @Override
    public Task findById(UUID id) {
        System.out.println("TaskRepositoryImpl.findById");
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
    public List<Task> findByFilter(List<String> filters){
        List<Task> filteredTasks = new ArrayList<>(taskStorage.values());

        String (filter: filters){
            switch (filter) {                    
                case "deadeline":
                case "reward":
                case "number of meetings":
                case "number of hunters requiered":
                default;
                break;
            }
        }




        return 
    }
}
