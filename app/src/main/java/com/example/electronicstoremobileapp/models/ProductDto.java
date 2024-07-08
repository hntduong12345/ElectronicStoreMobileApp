package com.example.electronicstoremobileapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    protected ProductDto(Parcel in) {
        ProductId = in.readString();
        ProductName = in.readString();
        Description = in.readString();
        DefaultPrice = in.readDouble();
        CategoryId = in.readString();
        Category = in.readParcelable(CategoryDto.class.getClassLoader());
        Manufacturer = in.readString();
        StorageAmount = in.readInt();
        SaleAmount = in.readInt();
        CurrentPrice = in.readDouble();
        IsOnSale = in.readByte() != 0;
        SaleEndDate = in.readString();
        RelativeUrl = in.readString();
    }
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
        dest.writeParcelable(Category, flags);
        dest.writeString(Manufacturer);
        dest.writeInt(StorageAmount);
        dest.writeInt(SaleAmount);
        dest.writeDouble(CurrentPrice);
        dest.writeByte((byte) (IsOnSale ? 1 : 0));
        dest.writeString(SaleEndDate);
        dest.writeString(RelativeUrl);
    }
    public static final Parcelable.Creator<ProductDto> CREATOR = new Parcelable.Creator<ProductDto>() {
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
