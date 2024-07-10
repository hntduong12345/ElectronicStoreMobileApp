package com.example.electronicstoremobileapp.admins.ui.orders.viewElements;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.content.ClipboardManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminOrderDetailListItemBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminOrderListItemBinding;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailAdapter extends BaseAdapter {
    Context parentContext;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;
    List<OrderDetailDto> orderDetails;
    public OrderDetailAdapter(Context parentContext, List<OrderDetailDto> orderDetails){
        this.parentContext = parentContext;
        this.orderDetails = orderDetails;
        this.layoutInflater = LayoutInflater.from(parentContext);
    }
    public void Clear(){
        orderDetails.clear();
        notifyDataSetChanged();
    }
    public void Fill(List<OrderDetailDto> list){
        orderDetails = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @Override
    public OrderDetailDto getItem(int position) {
        return orderDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminOrderDetailListItemBinding binding;
        if(convertView == null){
            binding = ListviewitemAdminOrderDetailListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        else{
            binding = (ListviewitemAdminOrderDetailListItemBinding) convertView.getTag();
        }
        OrderDetailDto orderDetail = getItem(position);
        binding.txtDetailTitle.setText("Detail #"+String.valueOf(position));
        binding.txtProductId.setText(orderDetail.ProductId);
        binding.txtQuantity.setText(String.valueOf(orderDetail.Quantity));
        binding.txtPrice.setText(String.valueOf(orderDetail.Price));
        binding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) parentContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Product Id",orderDetail.ProductId);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(parentContext, "Product Id copied!\n"+orderDetail.ProductId, Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
