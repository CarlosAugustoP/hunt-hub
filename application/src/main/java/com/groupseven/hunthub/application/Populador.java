package com.groupseven.hunthub.application;

import com.groupseven.hunthub.persistence.jpa.repository.UserJpaRepository;
import com.groupseven.hunthub.persistence.jpa.models.UserJpa;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Populador {

    private final UserJpaRepository userJpaRepository;

    public Populador(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @PostConstruct
    public void popular() {
        UserJpa user = new UserJpa();
        user.setName("hello");
        userJpaRepository.save(user);
    }
}
