package com.example.electronicstoremobileapp.admins.ui.orders.models;

import com.example.electronicstoremobileapp.admins.ui.orders.viewElements.OrderDetailAdapter;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.List;

public class OrderWrapperModel {
    public OrderDto Order;
    public boolean IsShown;
    public OrderDetailAdapter OrderDetailAdapter;
    public OrderWrapperModel(OrderDto order){
        this.Order = order;
        this.IsShown = false;
    }
}
