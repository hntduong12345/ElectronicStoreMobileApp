package com.example.electronicstoremobileapp.admins.ui.vouchers.models;

import com.google.gson.annotations.SerializedName;

public class VoucherUpdateDto {
    @SerializedName("voucherCode")
    public String VoucherCode;
    @SerializedName("expiryDate")
    public String ExpiryDate;
    @SerializedName("percentage")
    public int Percentage;
    @SerializedName("isAvailable")
    public boolean IsAvailable;

    public VoucherUpdateDto(String voucherCode, String expiryDate, int percentage, boolean isAvailable) {
        VoucherCode = voucherCode;
        ExpiryDate = expiryDate;
        Percentage = percentage;
        IsAvailable = isAvailable;
    }
}
