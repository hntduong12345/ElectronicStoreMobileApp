package com.example.electronicstoremobileapp.models;

import android.app.Application;

import java.util.List;

public class Cart {
    public ProductDto cartItem;
    public int quantity;

    public Cart(ProductDto cartItem, int quantity) {
        this.cartItem = cartItem;
        this.quantity = quantity;
    }

    public Cart() {

    }

    public void ChangeQuantity(int value){
        this.quantity += value;
    }
}
