package com.example.electronicstoremobileapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AccountDto implements Parcelable {
    @SerializedName("accountId")
    public String AccountId;
    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
    @SerializedName("email")
    public String Email;
    @SerializedName("password")
    public String Password;
    @SerializedName("address")
    public String Address;
    @SerializedName("phoneNumber")
    public String PhoneNumber;
    @SerializedName("role")
    public String Role;
    @SerializedName("status")
    public String Status;
    //public List<OrderDTO> Orders;


    public AccountDto(String accountId, String firstName, String lastName, String email, String password, String address, String phoneNumber, String role, String status) {
        AccountId = accountId;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        Address = address;
        PhoneNumber = phoneNumber;
        Role = role;
        Status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(AccountId);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(Email);
        dest.writeString(Password);
        dest.writeString(Address);
        dest.writeString(PhoneNumber);
        dest.writeString(Role);
        dest.writeString(Status);
    }

    protected AccountDto(Parcel in) {
        AccountId = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Email = in.readString();
        Password = in.readString();
        Address = in.readString();
        PhoneNumber = in.readString();
        Role = in.readString();
        Status = in.readString();
    }

    public static final Creator<AccountDto> CREATOR = new Creator<AccountDto>() {
        @Override
        public AccountDto createFromParcel(Parcel in) {
            return new AccountDto(in);
        }

        @Override
        public AccountDto[] newArray(int size) {
            return new AccountDto[size];
        }
    };
}
