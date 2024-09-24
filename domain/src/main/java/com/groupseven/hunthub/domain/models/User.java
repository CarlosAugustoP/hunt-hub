package com.groupseven.hunthub.domain.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private Long cpf;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String type;

    public User(String type, String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
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
