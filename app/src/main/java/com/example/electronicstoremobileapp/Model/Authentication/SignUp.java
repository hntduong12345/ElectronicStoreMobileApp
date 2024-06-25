package com.example.electronicstoremobileapp.Model.Authentication;

public class SignUp {
    public String Input;
    public String Password;

    public SignUp(String input, String password) {
        Input = input;
        Password = password;
    }

    public String getInput() {
        return Input;
    }

    public void setInput(String input) {
        Input = input;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
