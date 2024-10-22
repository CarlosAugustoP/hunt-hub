package com.groupseven.hunthub.domain.models;

public class User {

    public UserId id;

    public String cpf;

    public int points = 0;

    public String name;

    public String email;

    public String password;

    public User(String name, String email, String password, String cpf, UserId id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.id = id;
    }

    public User() {
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
