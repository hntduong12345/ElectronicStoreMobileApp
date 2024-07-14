package com.example.electronicstoremobileapp.models;

import android.app.Application;

import java.util.List;

public class CartList {
    public List<Cart> cartList;

    public CartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
