package com.example.electronicstoremobileapp.models.combos;

import com.example.electronicstoremobileapp.models.documents.ComboProducts;
import com.google.gson.annotations.SerializedName;

import java.io.Serial;
import java.util.List;

public class ComboDto {
    @SerializedName("comboId")
    public String ComboId;
    @SerializedName("name")
    public String Name;
    @SerializedName("products")
    public List<ComboProducts> Products;
    @SerializedName("price")
    public double Price;
    @SerializedName("isAvailable")
    public boolean IsAvailable;
}
