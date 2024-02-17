package com.example.myapplication.repository.models;

public class FioProfil {
    String fio;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public FioProfil(String fio, String email) {
        this.fio = fio;
        this.email = email;
    }
}
