package com.example.electronicstoremobileapp.models.orders;

import com.google.gson.annotations.SerializedName;

public class PaymentRespones {
    @SerializedName("orderId")
    public String OrderId;
    @SerializedName("paymentUrl")
    public String PaymentUrl;
}
