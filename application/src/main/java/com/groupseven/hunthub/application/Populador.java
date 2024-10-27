package com.groupseven.hunthub.application;

import com.groupseven.hunthub.persistence.jpa.repository.UserJpaRepository;
import com.groupseven.hunthub.persistence.jpa.repository.HunterJpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;
import com.groupseven.hunthub.persistence.jpa.models.HunterJpa;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Populador {

    private final UserJpaRepository userJpaRepository;
    private final HunterJpaRepository hunterJpaRepository;

    public Populador(UserJpaRepository userJpaRepository, HunterJpaRepository hunterJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.hunterJpaRepository = hunterJpaRepository;
    }

    @PostConstruct
    public void popular() {
    }
}
