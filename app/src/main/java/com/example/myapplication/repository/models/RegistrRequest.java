package com.example.myapplication.repository.models;

public class RegistrRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;
    String guard;
     public RegistrRequest (String firstName, String lastName, String email, String password){
         this.firstName =firstName;
         this.lastName = lastName;
         this.email=email;
         this.password=password;
         this.confirmPassword=password;
         this.guard="client";
     }

}
