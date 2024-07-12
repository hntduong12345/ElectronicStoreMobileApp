package com.example.electronicstoremobileapp.models.orders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateOrderDto {
    @SerializedName("totalPrice")
    public double TotalPrice;
    @SerializedName("accountId")
    public String AccountId;
    @SerializedName("orderDetails")
    public List<OrderDetailDto> OrderDetails;
    @SerializedName("truePrice")
    public double TruePrice;

    public CreateOrderDto(double totalPrice, String accountId, List<OrderDetailDto> orderDetails, double truePrice) {
        TotalPrice = totalPrice;
        AccountId = accountId;
        OrderDetails = orderDetails;
        TruePrice = truePrice;
    }
}
