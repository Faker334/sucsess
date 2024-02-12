package com.example.myapplication.repository.models;

public class LoginRequest {
    private String email;
    private String password;


    // Конструктор
    public LoginRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    // Геттеры и сеттеры
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