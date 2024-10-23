package com.groupseven.hunthub.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {
        "com.groupseven.hunthub.application",
        "com.groupseven.hunthub.persistence.jpa.repository",
        "com.groupseven.hunthub.persistence.jpa.models",
        "com.groupseven.hunthub.persistence.jpa.mapper",
        "com.groupseven.hunthub.domain.services",
        "com.groupseven.hunthub.presentation.backend",
        "com.groupseven.hunthub.presentation.backend.User",
        "com.groupseven.hunthub.domain.security"
})
@EnableJpaRepositories(basePackages = {
        "com.groupseven.hunthub.persistence.jpa.repository"
})


@EntityScan(basePackages = "com.groupseven.hunthub.persistence.jpa.models")
public class HunthubApplication {
    public static void main(String[] args) {
        SpringApplication.run(HunthubApplication.class, args);
    }
}
