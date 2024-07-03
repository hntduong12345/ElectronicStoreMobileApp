package com.example.electronicstoremobileapp.models;

import com.google.gson.annotations.SerializedName;

public class ProductDto {
    @SerializedName("productId")
    public String ProductId;
    @SerializedName("productName")
    public String ProductName;
    @SerializedName("description")
    public String Description;
    @SerializedName("defaultPrice")
    public double DefaultPrice;
    @SerializedName("categoryId")
    public String CategoryId;
    @SerializedName("category")
    public CategoryDto Category;
    @SerializedName("manufacturer")
    public String Manufacturer;
    @SerializedName("storageAmount")
    public int StorageAmount;
    @SerializedName("saleAmount")
    public int SaleAmount;
    @SerializedName("currentPrice")
    public double CurrentPrice;
    @SerializedName("isOnSale")
    public boolean IsOnSale;
    @SerializedName("saleEndDate")
    public String SaleEndDate;
    @SerializedName("relativeUrl")
    public String RelativeUrl;


}
