package com.example.electronicstoremobileapp.Adapters.order;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ListviewtitemUserOrderListItemBinding;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.List;

public class CustomerOrderAdapter extends BaseAdapter {

    Context parentContext;
    int layout;
    LayoutInflater layoutInflater;
    private List<OrderDto> orderList;

    public CustomerOrderAdapter(Context parentContext, List<OrderDto> orderList, int layout) {
        this.parentContext = parentContext;
        this.orderList = orderList;
        this.layout = layout;
        this.layoutInflater = LayoutInflater.from(parentContext);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewtitemUserOrderListItemBinding binding;
        if (convertView == null) {
            binding = ListviewtitemUserOrderListItemBinding.inflate(layoutInflater,parent,false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ListviewtitemUserOrderListItemBinding) convertView.getTag();
        }

        TextView txt_OrderId = convertView.findViewById(R.id.txt_OrderId);
        TextView txt_totalPrice = convertView.findViewById(R.id.txt_totalPrice);

        OrderDto order = orderList.get(position);

        txt_OrderId.setText("Order '" + order.OrderId+"'");
        txt_totalPrice.setText(order.TotalPrice+"");

        return convertView;
    }
}
