package com.example.electronicstoremobileapp.models.combos;

import com.example.electronicstoremobileapp.models.ProductDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetComboDto {
    @SerializedName("comboId")
    public String ComboId;
    @SerializedName("name")
    public String Name;
    @SerializedName("products")
    public List<ProductDto> Products;
    @SerializedName("price")
    public double Price;
    @SerializedName("IsAvailable")
    public boolean IsAvailable;
}
