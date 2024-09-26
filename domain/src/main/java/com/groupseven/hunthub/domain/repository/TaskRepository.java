package com.groupseven.hunthub.domain.repository;
import com.groupseven.hunthub.domain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    public void save (Task task);
    public Task findById(UUID id);
    public List<Task> findAll ();
    public void delete (UUID id);

}
