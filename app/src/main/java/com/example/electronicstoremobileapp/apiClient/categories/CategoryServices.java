package com.example.electronicstoremobileapp.apiClient.categories;


import com.example.electronicstoremobileapp.models.CategoryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryServices {
    @GET("api/Category/all")
    Call<List<CategoryDto>> GetAll();
}
