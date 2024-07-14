package com.example.electronicstoremobileapp.models.orders;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDto implements Parcelable {
    @SerializedName("orderId")
    public String OrderId;
    @SerializedName("totalPrice")
    public float TotalPrice;
    @SerializedName("accountId")
    public String AccountId;
    @SerializedName("orderDetails")
    public List<OrderDetailDto> OrderDetails;
    @SerializedName("status")
    public String Status;
    @SerializedName("truePrice")
    public float TruePrice;

    public OrderDto(String orderId, float totalPrice, String accountId, List<OrderDetailDto> orderDetails, String status, float truePrice) {
        OrderId = orderId;
        TotalPrice = totalPrice;
        AccountId = accountId;
        OrderDetails = orderDetails;
        Status = status;
        TruePrice = truePrice;
    }

    protected OrderDto(Parcel in) {
        OrderId = in.readString();
        TotalPrice = in.readFloat();
        AccountId = in.readString();
        Status = in.readString();
        TruePrice = in.readFloat();
    }

    public static final Creator<OrderDto> CREATOR = new Creator<OrderDto>() {
        @Override
        public OrderDto createFromParcel(Parcel in) {
            return new OrderDto(in);
        }

        @Override
        public OrderDto[] newArray(int size) {
            return new OrderDto[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(OrderId);
        dest.writeFloat(TotalPrice);
        dest.writeString(AccountId);
        dest.writeList(OrderDetails);
        dest.writeString(Status);
        dest.writeFloat(TruePrice);
    }
}

