package com.example.electronicstoremobileapp.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.google.gson.annotations.SerializedName;

public class ProductDto implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(ProductId);
        dest.writeString(ProductName);
        dest.writeString(Description);
        dest.writeDouble(DefaultPrice);
        dest.writeString(CategoryId);
        dest.writeString(Manufacturer);
        dest.writeInt(StorageAmount);
        dest.writeInt(SaleAmount);
        dest.writeDouble(CurrentPrice);
        dest.writeString(SaleEndDate);
        dest.writeString(RelativeUrl);
    }

    protected ProductDto(Parcel in) {
        ProductId = in.readString();
        ProductName = in.readString();
        Description = in.readString();
        DefaultPrice = in.readDouble();
        CategoryId = in.readString();
        Manufacturer = in.readString();
        StorageAmount = in.readInt();
        SaleAmount = in.readInt();
        CurrentPrice = in.readDouble();
        SaleEndDate = in.readString();
        RelativeUrl = in.readString();
    }

    public static final Creator<ProductDto> CREATOR = new Creator<ProductDto>() {
        @Override
        public ProductDto createFromParcel(Parcel in) {
            return new ProductDto(in);
        }

        @Override
        public ProductDto[] newArray(int size) {
            return new ProductDto[size];
        }
    };
}
