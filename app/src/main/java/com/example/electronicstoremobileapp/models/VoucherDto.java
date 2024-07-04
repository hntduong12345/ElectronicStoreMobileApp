package com.example.electronicstoremobileapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class VoucherDto implements Parcelable {
    @SerializedName("voucherId")
    public String VoucherId;
    @SerializedName("totalPrice")
    public float TotalPrice;
    @SerializedName("account")
    public AccountDto Account;
    @SerializedName("voucherCode")
    public String VoucherCode;
    @SerializedName("expiryDate")
    public String ExpiryDate;
    @SerializedName("createdDate")
    public String CreatedDate;
    @SerializedName("type")
    public String Type;
    @SerializedName("percentage")
    public float Percentage;
    @SerializedName("moneyThreshold")
    public float MoneyThreshold;

    @SerializedName("isAvailable")
    public boolean IsAvailable;

    public VoucherDto(String voucherId, float totalPrice, AccountDto account, String voucherCode, String expiryDate, String createdDate, String type, float percentage, float moneyThreshold, boolean isAvailable) {
        VoucherId = voucherId;
        TotalPrice = totalPrice;
        Account = account;
        VoucherCode = voucherCode;
        ExpiryDate = expiryDate;
        CreatedDate = createdDate;
        Type = type;
        Percentage = percentage;
        MoneyThreshold = moneyThreshold;
        IsAvailable = isAvailable;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(VoucherId);
        dest.writeFloat(TotalPrice);
        dest.writeTypedObject(Account,PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeString(VoucherCode);
        dest.writeString(ExpiryDate);
        dest.writeString(CreatedDate);
        dest.writeString(Type);
        dest.writeFloat(Percentage);
        dest.writeFloat(MoneyThreshold);
        dest.writeBoolean(IsAvailable);
    }

    protected VoucherDto(Parcel in) {
        VoucherId = in.readString();
        TotalPrice = in.readFloat();
        Account = in.readTypedObject(AccountDto.CREATOR);
        VoucherCode = in.readString();
        ExpiryDate = in.readString();
        CreatedDate = in.readString();
        Type = in.readString();
        Percentage = in.readFloat();
        MoneyThreshold = in.readFloat();
        IsAvailable = in.readBoolean();
    }

    public static final Creator<VoucherDto> CREATOR = new Creator<VoucherDto>() {
        @Override
        public VoucherDto createFromParcel(Parcel in) {
            return new VoucherDto(in);
        }

        @Override
        public VoucherDto[] newArray(int size) {
            return new VoucherDto[size];
        }
    };
}
