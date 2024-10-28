package com.groupseven.hunthub.domain.repository;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.PO;

public interface PoRepository {

    void save(PO po);

    PO findById(UUID id);

    List<PO> findAll();

    void delete(UUID id);
}