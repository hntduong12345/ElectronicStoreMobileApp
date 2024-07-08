package com.example.electronicstoremobileapp.models;

import android.app.Application;

import java.util.List;

public class Cart extends Application {
    public ProductDto cartItem;
    public int quantity;

    public Cart(ProductDto cartItem, int quantity) {
        this.cartItem = cartItem;
        this.quantity = quantity;
    }

    public void ChangeQuantity(int value){
        this.quantity += value;
    }
}
