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

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }
}
