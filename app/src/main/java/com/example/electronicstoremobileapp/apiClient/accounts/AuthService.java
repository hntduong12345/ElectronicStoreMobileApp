package com.example.electronicstoremobileapp.apiClient.accounts;



import com.example.electronicstoremobileapp.models.authentication.Login;
import com.example.electronicstoremobileapp.models.authentication.LoginResponse;
import com.example.electronicstoremobileapp.models.authentication.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/customer/login")
    Call<LoginResponse> Login(@Body Login login);

    @POST("/api/customer/register")
    Call<LoginResponse> Register(@Body SignUp signUp);
}
