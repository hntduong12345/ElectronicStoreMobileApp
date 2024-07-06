package com.example.electronicstoremobileapp.apiClient.combos;

import com.example.electronicstoremobileapp.models.combos.ComboDto;
import com.example.electronicstoremobileapp.models.combos.CreateComboDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComboService {
    @GET("api/combos")
    Call<List<ComboDto>> GetAll();

    @GET("api/combos/{id}")
    Call<ComboDto> GetById(@Path("id") String id);

    @GET("api/combos/availability")
    Call<List<ComboDto>> GetAvailableCombos();

    @POST("api/combos")
    Call<String> Create(@Body CreateComboDto createComboDto);

    @PATCH("api/combos/{id}")
    Call<String> Update(@Path("id") String id, @Body ComboDto comboDto);

    @PATCH("api/combos/{id}/status")
    Call<String> ChangeStatus(@Path("id") String id);

    @DELETE("api/combos/{id}")
    Call<String> Delete(@Path("id") String id);
}
