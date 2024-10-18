package com.groupseven.hunthub.domain.repository;

import java.util.List;
import com.groupseven.hunthub.domain.models.PO;

public interface PoRepository {

    void save(PO po);

    PO findById(Long id);

    List<PO> findAll();

    void delete(Long id);
}