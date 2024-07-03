package com.example.electronicstoremobileapp.Model.Authentication.Interface;



import com.example.electronicstoremobileapp.Model.Authentication.Login;
import com.example.electronicstoremobileapp.Model.Authentication.LoginResponse;
import com.example.electronicstoremobileapp.Model.Authentication.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/customer/login")
    Call<LoginResponse> Login(@Body Login login);

    @POST("/api/customer/register")
    Call<LoginResponse> Register(@Body SignUp signUp);
}
