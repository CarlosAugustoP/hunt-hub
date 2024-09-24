package com.groupseven.hunthub.domain.repository;
import com.groupseven.hunthub.domain.models.PO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PORepository extends JpaRepository<PO, UUID> {
    
    
}
