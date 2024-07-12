package com.example.electronicstoremobileapp.apiClient.orders;

import com.example.electronicstoremobileapp.models.orders.CreateOrderDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;
import com.example.electronicstoremobileapp.models.orders.PaymentRespones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderServices {
    String ORDERS = "api/orders";
    @GET(ORDERS)
    Call<List<OrderDto>> GetAllOrders();

    @GET(ORDERS + "/account/{accountId}")
    Call<List<OrderDto>> GetOrdersByAccount(@Path("accountId") Object accountId);

    @GET(ORDERS + "/{id}")
    Call<List<OrderDto>> GetOrderById(@Path("id") Object id);

    @POST(ORDERS)
    Call<PaymentRespones> CreateOrder(@Body CreateOrderDto createOrderDto);

    @PATCH(ORDERS + "/{id}/status")
    Call<List<OrderDto>> ChangeOrderStatus(@Path("id") Object id, @Body String status);
}
