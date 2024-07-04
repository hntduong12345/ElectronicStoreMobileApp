package com.example.electronicstoremobileapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CategoryDto implements Parcelable {
    @SerializedName("categoryId")
    public String CategoryId;
    @SerializedName("categoryName")
    public String CategoryName;
    @SerializedName("categoryDescription")
    public String CategoryDescription;

    public CategoryDto(String categoryId, String categoryName, String categoryDescription) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryDescription = categoryDescription;
    }

    public CategoryDto() {
    }

    protected CategoryDto(Parcel in) {
        CategoryId = in.readString();
        CategoryName = in.readString();
        CategoryDescription = in.readString();
    }
    @NonNull
    @Override
    public String toString() {
        return CategoryName.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(CategoryId);
        dest.writeString(CategoryName);
        dest.writeString(CategoryDescription);
    }
    public static final Parcelable.Creator<CategoryDto> CREATOR = new Parcelable.Creator<CategoryDto>() {
        @Override
        public CategoryDto createFromParcel(Parcel in) {
            return new CategoryDto(in);
        }

        @Override
        public CategoryDto[] newArray(int size) {
            return new CategoryDto[size];
        }
    };
}
