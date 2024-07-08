package com.example.electronicstoremobileapp.models.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryProductDto {
    @SerializedName("total")
    public int Total;
    @SerializedName("values")
    public List<ProductDto> Values;
}
