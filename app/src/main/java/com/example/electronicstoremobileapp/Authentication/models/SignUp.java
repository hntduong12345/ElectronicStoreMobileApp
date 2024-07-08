package com.example.electronicstoremobileapp.Authentication.models;

public class SignUp {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public SignUp(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
