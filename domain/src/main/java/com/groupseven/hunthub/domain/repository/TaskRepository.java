package com.groupseven.hunthub.domain.repository;
import com.groupseven.hunthub.domain.models.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
        
    
}
