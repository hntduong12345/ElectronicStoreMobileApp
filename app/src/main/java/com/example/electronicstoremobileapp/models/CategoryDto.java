package com.example.electronicstoremobileapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {
    @SerializedName("categoryId")
    public String CategoryId;
    @SerializedName("categoryName")
    public String CategoryName;
    @SerializedName("categoryDescription")
    public String CategoryDescription;

    @NonNull
    @Override
    public String toString() {
        return CategoryName.toString();
    }
}
