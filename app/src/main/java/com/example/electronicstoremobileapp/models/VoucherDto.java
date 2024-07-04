package com.example.electronicstoremobileapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class VoucherDto implements Parcelable {
    @SerializedName("voucherId")
    public String VoucherId;
    @SerializedName("account")
    public AccountDto Account;
    @SerializedName("voucherCode")
    public String VoucherCode;
    @SerializedName("expiryDate")
    public String ExpiryDate;
    @SerializedName("createdDate")
    public String CreatedDate;
    @SerializedName("percentage")
    public float Percentage;
    @SerializedName("isAvailable")
    public boolean IsAvailable;

    public VoucherDto(String voucherId, float totalPrice, AccountDto account, String voucherCode, String expiryDate, String createdDate, float percentage, float moneyThreshold, boolean isAvailable) {
        VoucherId = voucherId;
        Account = account;
        VoucherCode = voucherCode;
        ExpiryDate = expiryDate;
        CreatedDate = createdDate;
        Percentage = percentage;
        IsAvailable = isAvailable;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(VoucherId);
        dest.writeTypedObject(Account,PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeString(VoucherCode);
        dest.writeString(ExpiryDate);
        dest.writeString(CreatedDate);
        dest.writeFloat(Percentage);
        dest.writeBoolean(IsAvailable);
    }

    protected VoucherDto(Parcel in) {
        VoucherId = in.readString();
        Account = in.readTypedObject(AccountDto.CREATOR);
        VoucherCode = in.readString();
        ExpiryDate = in.readString();
        CreatedDate = in.readString();
        Percentage = in.readFloat();
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
