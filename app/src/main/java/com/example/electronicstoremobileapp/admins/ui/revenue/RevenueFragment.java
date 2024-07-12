package com.example.electronicstoremobileapp.admins.ui.revenue;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.orders.models.OrderWrapperModel;
import com.example.electronicstoremobileapp.admins.ui.orders.viewElements.OrderAdapter;
import com.example.electronicstoremobileapp.admins.ui.vouchers.viewElements.VoucherAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminOrderListBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminRevenueBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherListBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueFragment extends Fragment {
    FragmentAdminRevenueBinding binding;
    int orderMade;
    float revenueTotal;
    Map<String, Integer> productCount;
    Toolbar toolbar;
    NavHostFragment parentFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentAdminRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        parentFragment = (NavHostFragment) getParentFragment();
        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_order_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });
        orderMade = 0;
        productCount = new HashMap<>();
        revenueTotal = 0;
        GetOrders();
        binding.txtTotalOrder.setText(String.valueOf(orderMade));
        StringBuilder text = new StringBuilder();
        LinkedHashMap<String, Integer> sorted = productCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1,e2) -> e1, LinkedHashMap::new));
        sorted.forEach((k,v) -> {
            text.append(k + ": "+String.valueOf(v)+"\n");
        });
        binding.txtProductSold.setText(text.toString());
        binding.txtRevenue.setText(String.valueOf(revenueTotal));
        return root;
    }

    void GetOrders(){
        Call<List<OrderDto>> call = ApiClient.getServiceClient(OrderServices.class).GetAllOrders();
        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                List<OrderDto> responseBody = response.body();
                for (OrderDto order : responseBody){
                    if(order.Status.equals("Completed")){
                        orderMade += 1;
                        revenueTotal += order.TotalPrice;
                        for (OrderDetailDto detail : order.OrderDetails){
                            //TODO: REPLACE ID with ACTUAL PRODUCT
                            Integer quantity = productCount.get(detail.ProductId);
                            Integer value = detail.Quantity + ((quantity == null) ? 1 : quantity );
                            productCount.put(detail.ProductId, value);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth voucher");
            }
        });
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
    private boolean menuItemClicked(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.toolbarBack){
            NavHostFragment parentFragment = (NavHostFragment)  getParentFragment();
            if(parentFragment != null){
                NavController navController = parentFragment.getNavController();
                navController.navigate(R.id.action_navigation_revenue_to_profile_detail);
            }
            return true;
        }
        return false;
    }
}
