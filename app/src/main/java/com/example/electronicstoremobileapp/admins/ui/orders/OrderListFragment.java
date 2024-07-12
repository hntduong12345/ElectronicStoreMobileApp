package com.example.electronicstoremobileapp.admins.ui.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherListBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListFragment extends Fragment {
    FragmentAdminOrderListBinding binding;
    ArrayList<String> sortingOrder;
    List<OrderWrapperModel> wrappers;
    Toolbar toolbar;
    Spinner dropdownSorting;
    OrderAdapter orderAdapter;
    NavHostFragment parentFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentAdminOrderListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        wrappers = new ArrayList();
        parentFragment = (NavHostFragment) getParentFragment();
        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_order_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });
        dropdownSorting = binding.dropdownSorting;
        sortingOrder = new ArrayList<String>();
        sortingOrder.add("Latest");
        sortingOrder.add("Processing");
        sortingOrder.add("Cancelled");
        sortingOrder.add("Completed");
        GetOrders("Latest");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, sortingOrder);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dropdownSorting.setAdapter(adapter);
        dropdownSorting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropdownSorting.setSelection(position);
                String status = sortingOrder.get(position);
                orderAdapter.ChangeFilter(status);
                GetOrders(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        orderAdapter = new OrderAdapter(this.getContext(), wrappers, "Latest", parentFragment);
        binding.ListViewOrder.setAdapter(orderAdapter);
        return root;
    }

    void GetOrders(String status){
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
                wrappers.clear();
                for (OrderDto order : responseBody){
                    if(status.equals("Latest") || order.Status.equals(status) || (order.Status.equals("Paid") && status.equals("Processing")))    {
                        wrappers.add(new OrderWrapperModel(order));
                    }
                }
                orderAdapter.notifyDataSetChanged();
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
                navController.navigate(R.id.action_navigation_order_list_to_profile_detail);
            }
            return true;
        }
        return false;
    }
}
