package com.example.electronicstoremobileapp.apiClient.categories;


import com.example.electronicstoremobileapp.admins.ui.categories.models.CreateCategoryDto;
import com.example.electronicstoremobileapp.admins.ui.categories.models.UpdateCategoryDto;
import com.example.electronicstoremobileapp.models.CategoryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryServices {
    @GET("api/Category/all")
    Call<List<CategoryDto>> GetAll();
    @POST("api/Category")
    Call<CategoryDto> Create(@Body CreateCategoryDto categoryModel);
    @PUT("api/Category/{categoryId}")
    Call<Void> Update(@Path("categoryId") String categoryId, @Body UpdateCategoryDto updateModel);
    @DELETE("api/Category/{categoryId}")
    Call<Void> Delete(@Path("categoryId") String categoryId);
}
