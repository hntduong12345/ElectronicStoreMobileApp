package com.example.electronicstoremobileapp.ui.customer_ui.UserPage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.Adapters.order.CustomerOrderAdapter;
import com.example.electronicstoremobileapp.Adapters.product.ProductAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentOrderDetailBinding;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.Triple;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {

    Context currentContext;
    FragmentOrderDetailBinding binding;
    List<ProductDto> productDtoList = new ArrayList<>();
    public NavController navController;
    ProductAdapter adapter;

    OrderDto order;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    public static OrderDetailFragment newInstance() {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            OrderDto getOrder = (OrderDto) getArguments().get("Order");
            this.order = getOrder;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fecthData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_orderDetailFragment_to_userOrdersList);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }

    private void fecthData(){

        Call<List<ProductDto>> call = ApiClient.getServiceClient(ProductServices.class).GetAll();
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth orders with status code " + code);
                    return;
                }
                productDtoList.clear();;
                productDtoList.addAll(response.body());

                List<ProductDto> filtered = new ArrayList<>();

                for (OrderDetailDto orderDetail: order.OrderDetails) {
                    try {
                        ProductDto p = productDtoList.stream().filter(x -> x.ProductId.equals(orderDetail.ProductId)).findAny().get();
                        if (p != null) {
                            filtered.add(p);
                        }
                        else filtered.add(null);
                    } catch (Exception e) {
                        filtered.add(null);
                    }
                }

                adapter = new ProductAdapter(currentContext, filtered, R.layout.listviewtitem_user_order_list_item);
                binding.listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth orders");
                return;
            }
        });
    }
}