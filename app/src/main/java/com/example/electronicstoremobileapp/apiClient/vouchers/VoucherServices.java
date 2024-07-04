package com.example.electronicstoremobileapp.apiClient.vouchers;

import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface VoucherServices {
    @GET("api/Voucher/get-all")
    Call<List<VoucherDto>> GetAll();

    @Multipart
    @POST("api/Product")
    Call<ProductDto> Create(
            @Part("ProductName") RequestBody productName,
            @Part("Description") RequestBody description,
            @Part("DefaultPrice") RequestBody defaultPrice,
            @Part("CategoryId") RequestBody categoryId,
            @Part("Manufacturer") RequestBody manufacturer,
            @Part("StorageAmount") RequestBody storageAmount,
            @Part MultipartBody.Part imageFile
    );

}
