package com.example.springboot.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupForm {
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @NotBlank
    @Size(min = 1, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
