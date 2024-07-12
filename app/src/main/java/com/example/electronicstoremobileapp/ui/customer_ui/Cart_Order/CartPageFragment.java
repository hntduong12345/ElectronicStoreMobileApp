package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronicstoremobileapp.Adapters.cart.CartAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.orders.OrderServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.example.electronicstoremobileapp.models.PagingResponseDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.models.orders.CreateOrderDto;
import com.example.electronicstoremobileapp.models.orders.OrderDetailDto;
import com.example.electronicstoremobileapp.models.orders.OrderDto;
import com.example.electronicstoremobileapp.models.orders.PaymentRespones;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartPageFragment extends Fragment {

    FragmentCartPageBinding binding;

    List<Cart> cartList;
    CartAdapter cartAdapter;
    Context currentContext;

    SharedPreferences sharedPreferences;
    Gson gson = new Gson();
    double totalPrice;

    public CartPageFragment() {
        // Required empty public constructor
    }


    public static CartPageFragment newInstance() {
        CartPageFragment fragment = new CartPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        currentContext = this.getContext();

        cartList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("CartData", Context.MODE_PRIVATE);
        fetchData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.clear();
                cartAdapter.notifyDataSetChanged();
                updateLocalData();
            }
        });

        binding.buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRequest();
            }
        });
    }

    public void UpdateList(List<Cart> items){
        cartList.clear();
        cartList.addAll(items);
        cartAdapter.notifyDataSetChanged();

        updateLocalData();
    }

    private void fetchData() {
        try{
            cartList.clear();
            String dataJson = sharedPreferences.getString("CartObject", "");

            if (!TextUtils.equals(dataJson, "[]") && !TextUtils.isEmpty(dataJson)) {
                Type listType = new TypeToken<List<Cart>>(){}.getType();
                cartList = gson.fromJson(dataJson, listType);

                cartAdapter = new CartAdapter(currentContext, cartList, R.layout.component_cart_item_row, CartPageFragment.this);
                binding.listViewListCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e){
            e.getMessage();
        }

        UpdateCartUI();
    }

    public void updateLocalData() {
        String jsonData = gson.toJson(cartList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CartObject", jsonData);
        editor.apply();

        UpdateCartUI();
    }

    private void UpdateCartUI() {
        double total = 0;
        for (Cart item : cartList) {
            total += item.GetTotalCost();
        }

        totalPrice = total;

        binding.textViewTotalCost.setText(String.valueOf(total + " đ"));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void SendRequest(){
        List<OrderDetailDto> orderDetails = cartList.stream().map(c ->
                new OrderDetailDto(c.cartItem.ProductId, c.quantity, c.cartItem.DefaultPrice))
                .collect(Collectors.toList());

        cartList.clear();
        updateLocalData();

        //Change to AccId of Authen user
        CreateOrderDto request = new CreateOrderDto(totalPrice, "6671a8961739bcd25b1b2e71", orderDetails, totalPrice);

        Call<PaymentRespones> call = ApiClient.getServiceClient(OrderServices.class).CreateOrder(request);
        call.enqueue(new Callback<PaymentRespones>() {
            @Override
            public void onResponse(Call<PaymentRespones> call, Response<PaymentRespones> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                PaymentRespones paymentRespones = response.body();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(paymentRespones.PaymentUrl)));
            }

            @Override
            public void onFailure(Call<PaymentRespones> call, Throwable throwable) {

            }
        });

    }
}