package com.groupseven.hunthub.domain.models;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @NotNull
    public Long cpf;

    @NotNull
    @Size(min = 2, max = 50)
    public String name;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String password;

    @NotNull
    public String type;

    public User(String type, String name, String email, String password, Long cpf) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.cpf = cpf;
    }
 

    public User(){
    }

    public String getType (){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
