package com.example.electronicstoremobileapp.admins.ui.categories.models;

public class UpdateCategoryDto {
    public String CategoryName;
    public String CategoryDescription;
    public UpdateCategoryDto(String categoryName, String categoryDescription) {
        CategoryName = categoryName;
        CategoryDescription = categoryDescription;
    }

    public UpdateCategoryDto() {
    }
}
