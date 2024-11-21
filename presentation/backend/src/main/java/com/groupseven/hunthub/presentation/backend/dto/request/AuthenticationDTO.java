package com.groupseven.hunthub.presentation.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {
    @NotBlank(message = "email nao pode ser vazio")
    @Email(message = "precisa ser um email válido")
    private String email;

    @NotBlank(message = "senha nao pode ser vazia")
    @Size(min = 4, max = 20)
    private String password;


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
