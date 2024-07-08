package com.example.electronicstoremobileapp.models.orders;

import com.google.gson.annotations.SerializedName;

public class OrderDetailDto {
    @SerializedName("productId")
    public String ProductId;
    @SerializedName("quantity")
    public int Quantity;
    @SerializedName("price")
    public float Price;
}
