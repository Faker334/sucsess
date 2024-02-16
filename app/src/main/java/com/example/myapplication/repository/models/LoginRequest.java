package com.example.myapplication.repository.models;

public class LoginRequest {
    private String email;
    private String password;


    // Конструктор
    public LoginRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }



}