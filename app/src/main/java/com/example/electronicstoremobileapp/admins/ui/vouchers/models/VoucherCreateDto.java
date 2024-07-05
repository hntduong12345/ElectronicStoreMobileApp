package com.example.electronicstoremobileapp.admins.ui.vouchers.models;

import com.example.electronicstoremobileapp.models.AccountDto;
import com.google.gson.annotations.SerializedName;

public class VoucherCreateDto {
    @SerializedName("voucherCode")
    public String VoucherCode;
    @SerializedName("expiryDate")
    public String ExpiryDate;
    @SerializedName("createdDate")
    public String CreatedDate;
    @SerializedName("percentage")
    public int Percentage;
    @SerializedName("isAvailable")
    public boolean IsAvailable;

    public VoucherCreateDto(String voucherCode, String expiryDate, int percentage, boolean isAvailable) {
        VoucherCode = voucherCode;
        ExpiryDate = expiryDate;
        Percentage = percentage;
        IsAvailable = isAvailable;
    }
}
