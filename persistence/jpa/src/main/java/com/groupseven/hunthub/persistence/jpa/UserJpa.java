package com.groupseven.hunthub.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.groupseven.hunthub.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Entity
@Table(name="USER")
class UserJpa {
    @Id
    public UUID id;

    public Long cpf;

    public int points = 0;

    public String name;

    public String email;

    public String password;
}

interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {}

@Repository
class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserJpaRepository repository;
}