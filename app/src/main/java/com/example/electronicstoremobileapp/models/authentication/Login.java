package com.example.electronicstoremobileapp.models.authentication;

public class Login {
    public String input;
    public String password;

    public Login(String input, String password) {
        this.input = input;
        this.password = password;
    }
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
