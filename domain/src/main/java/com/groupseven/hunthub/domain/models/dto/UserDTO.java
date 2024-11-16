package com.groupseven.hunthub.domain.models.dto;

import java.util.UUID;

public class UserDTO {
    public String name;
    public String email;
    public int points;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
