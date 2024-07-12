package com.example.electronicstoremobileapp.admins.ui.orders.viewElements;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.orders.OrderListFragment;
import com.example.electronicstoremobileapp.admins.ui.orders.models.OrderWrapperModel;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminOrderListItemBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminVoucherListItemBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends BaseAdapter {
    Context parentContext;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;
    List<OrderWrapperModel> orders;
    String filter;
    public OrderAdapter(Context parentContext, List<OrderWrapperModel> orders, String filter, NavHostFragment parentNavigationFragment){
        this.parentContext = parentContext;
        this.orders = orders;
        this.filter = filter;
        this.layoutInflater = LayoutInflater.from(parentContext);
        this.parentNavigationFragment = parentNavigationFragment;
    }
    public void ChangeFilter(String filter){
        this.filter = filter;
    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public OrderWrapperModel getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminOrderListItemBinding binding;
        if(convertView == null){
            binding = ListviewitemAdminOrderListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        else{
            binding = (ListviewitemAdminOrderListItemBinding) convertView.getTag();
        }
        OrderWrapperModel wrapper = getItem(position);
        OrderDto order = wrapper.Order;
        binding.txtOrderId.setText(order.OrderId);
        binding.txtCustomer.setText(order.AccountId);
        binding.txtOrderStatus.setText(order.Status);
        binding.txtTotalPrice.setText("Total: "+order.TotalPrice+", True Price:"+order.TruePrice);
        if(!order.Status.equals("Paid")){
            binding.btnOption.setVisibility(View.INVISIBLE);
        }
        binding.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(parentContext, binding.btnOption);
                menu.getMenuInflater().inflate(R.menu.admin_order_fragment_choice_popup, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.OrderComplete){
                            OrderHandler(position, order.OrderId,true);
                        }
                        if(item.getItemId() == R.id.OrderCancel){
                            OrderHandler(position, order.OrderId,false);
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
        binding.btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrapper.IsShown){
                    wrapper.OrderDetailAdapter.Clear();
                }
                else{
                    List<OrderDetailDto> details = new ArrayList(wrapper.Order.OrderDetails);
                    if(wrapper.OrderDetailAdapter == null){
                        wrapper.OrderDetailAdapter = new OrderDetailAdapter(parentContext,details);
                        binding.listViewOrderDetail.setAdapter(wrapper.OrderDetailAdapter);
                    }
                    else{
                        wrapper.OrderDetailAdapter.Fill(details);
                    }
                }
                wrapper.IsShown = !wrapper.IsShown;
            }
        });

        return convertView;

    }


    void OrderHandler(int pos,String id, boolean complete){
        String status = complete ? "Completed" : "Cancelled";
        Call call = ApiClient.getServiceClient(OrderServices.class).ChangeOrderStatus(id,status);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                int code = response.code();
                if (code < 200 || code > 300){
                    Log.println(Log.ERROR, "API ERROR", "Error in update order with status code " + code);
                    return;
                }
                else{
                    if(filter.equals("Processing")){
                        orders.remove(pos);
                    }
                    else{
                        OrderWrapperModel newWrapper = orders.get(pos);
                        newWrapper.Order.Status = status;
                        orders.set(pos,newWrapper);
                    }
                    notifyDataSetChanged();
                    Toast.makeText(parentContext, "Order "+status, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in update order");
            }
        });
    }
}
