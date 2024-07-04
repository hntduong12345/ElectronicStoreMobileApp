package com.example.electronicstoremobileapp.admins.ui.accounts.models;

public class RegisterAccountModel {
    public String Email;
    public String Password;
    public String Firstname;
    public String Lastname;

    public RegisterAccountModel(String email, String password, String firstname, String lastname) {
        Email = email;
        Password = password;
        Firstname = firstname;
        Lastname = lastname;
    }

    public RegisterAccountModel() {
    }
}
