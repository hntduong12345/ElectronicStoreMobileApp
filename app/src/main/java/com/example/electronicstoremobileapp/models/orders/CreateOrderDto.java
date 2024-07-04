package com.example.electronicstoremobileapp.models.orders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateOrderDto {
    @SerializedName("totalPrice")
    public float TotalPrice;
    @SerializedName("accountId")
    public String AccountId;
    @SerializedName("orderDetails")
    public List<OrderDetailDto> OrderDetails;
    @SerializedName("truePrice")
    public float TruePrice;
}
