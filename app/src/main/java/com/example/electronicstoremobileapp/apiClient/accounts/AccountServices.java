package com.example.electronicstoremobileapp.apiClient.accounts;


import com.example.electronicstoremobileapp.models.AccountDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//@Header("Content-Type:application/json")
public interface AccountServices {
//    @Header("Content-Type:application/json")

    //    @GET("get-all")
//    Call<List<AccountDto>> GetAll();
    @GET("api/admin/get-all-user")
    Call<List<AccountDto>> GetAllUser();

    @GET("api/admin/get-all-admin")
    Call<List<AccountDto>> GetAllAdmin();

    @GET("api/admin/get-all-staff")
    Call<List<AccountDto>> GetAllStaff();

    @GET("api/admin/get-all-customer")
    Call<List<AccountDto>> GetAllCustomer();

//    @POST("api/admin/create-admin")
//    Call<Void> CreateAdmin(@Body RegisterAccountModel model);
//
//    @POST("api/admin/create-staff")
//    Call<Void> CreateStaff(@Body RegisterAccountModel model);
//
//
//    @PUT("api/admin/change-status/{id}")
//    Call<Void> Ban(@Path("id") String profileId);
}
