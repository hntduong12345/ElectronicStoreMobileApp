package com.example.electronicstoremobileapp.apiClient.vouchers;

import com.example.electronicstoremobileapp.admins.ui.vouchers.models.VoucherCreateDto;
import com.example.electronicstoremobileapp.admins.ui.vouchers.models.VoucherUpdateDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface VoucherServices {
    @GET("api/Voucher/get-all")
    Call<List<VoucherDto>> GetAll();

    @GET("api/Voucher/get-voucher/{id}")
    Call<VoucherDto> Get(@Path("id") String id);

    @Multipart
    @POST("api/Voucher/create/{accountId}")
    Call Create(
            @Path("AccountId") String accountId,
            @Body VoucherCreateDto voucher
    );

    @Multipart
    @POST("api/Voucher/update{id}")
    Call Update(
            @Path("AccountId") String id,
            @Body VoucherUpdateDto voucher
    );


}
