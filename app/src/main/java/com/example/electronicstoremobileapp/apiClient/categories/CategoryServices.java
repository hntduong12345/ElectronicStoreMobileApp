package com.example.electronicstoremobileapp.apiClient.categories;


import com.example.electronicstoremobileapp.admins.ui.categories.models.CreateCategoryDto;
import com.example.electronicstoremobileapp.models.CategoryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryServices {
    @GET("api/Category/all")
    Call<List<CategoryDto>> GetAll();
    @POST("api/Category")
    Call<CategoryDto> Create(@Body CreateCategoryDto categoryModel);
}
