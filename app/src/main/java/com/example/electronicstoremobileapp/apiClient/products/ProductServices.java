package com.example.electronicstoremobileapp.apiClient.products;

import com.example.electronicstoremobileapp.models.ProductDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

//@Header("Content-Type:application/json")
public interface ProductServices {
//    @Header("Content-Type:application/json")

    @GET("api/Product/all")
    Call<List<ProductDto>> GetAll();

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

    @Multipart
    @PUT("api/Product/{productId}")
    Call<ProductDto> Update(@Path("productId") String productId);

    @GET("api/Product/category")
    Call<List<ProductDto>> GetProductbyCategory(@Query("categoryId") String categoryId, @Query("start") int start , @Query("pageSize") int pageSize);
}
