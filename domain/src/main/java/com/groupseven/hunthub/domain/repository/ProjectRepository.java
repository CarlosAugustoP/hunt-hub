package com.groupseven.hunthub.domain.repository;
import com.groupseven.hunthub.domain.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    
    
}
