package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronicstoremobileapp.Adapters.cart.CartAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.example.electronicstoremobileapp.models.CartList;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartPageFragment extends Fragment {

    FragmentCartPageBinding binding;
    public NavController navController;

    List<Cart> cartList;
    CartAdapter cartAdapter;
    SharedPreferences sharedPreferences;
    Context currentContext;

    Gson gson = new Gson();

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
    }

    private void fetchData() {
        cartList.clear();
        String dataJson = sharedPreferences.getString("CartObject", "");

        if (!TextUtils.equals(dataJson, "[]") && !TextUtils.isEmpty(dataJson)) {
            CartList localCartList = gson.fromJson(dataJson, CartList.class);
            cartList = localCartList.cartList;

            cartAdapter = new CartAdapter(currentContext, cartList, R.layout.component_cart_item_row);
            binding.listViewListCart.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }
    }

    public void updateLocalData() {
        String jsonData = gson.toJson(cartList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CartObject", jsonData);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}