package com.example.electronicstoremobileapp.Repository;

import com.example.electronicstoremobileapp.APIClient;
import com.example.electronicstoremobileapp.Model.Authentication.Interface.AuthService;

public class AuthRepository {
    public static AuthService getAuthService(){
        return APIClient.getClient().create(AuthService.class);
    }
}
