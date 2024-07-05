package com.example.electronicstoremobileapp.admins.ui.categories.models;

public class CreateCategoryDto {

    public String CategoryName;
    public String CategoryDescription;

    public CreateCategoryDto(String categoryName, String categoryDescription) {
        CategoryName = categoryName;
        CategoryDescription = categoryDescription;
    }

    public CreateCategoryDto() {
    }
}
