package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronicstoremobileapp.Adapters.cart.CartAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.example.electronicstoremobileapp.models.CartList;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    VoucherDto selected = null;
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
        selected = null;
        Bundle args = getArguments();
        if(args != null){
            String voucherSelectedId = args.getString("VoucherSelectedId");
            if(voucherSelectedId != null){
                GetSelectedVoucher(voucherSelectedId);
            }
        }
        cartList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("CartData", Context.MODE_PRIVATE);
        fetchData();
        binding.constraintLayoutVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment host = (NavHostFragment) getParentFragment();
                NavOptions option = new NavOptions.Builder()
                        .setEnterAnim(R.anim.enter_from_right)
                                .setExitAnim(R.anim.exit_to_left)
                                        .build();
                host.getNavController().navigate(R.id.action_cartPageFragment_to_cartVoucherFragment,null,option);
            }
        });
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

    }

    public void updateLocalData() {
        String jsonData = gson.toJson(cartList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CartObject", jsonData);
        editor.apply();
    }
    void GetSelectedVoucher(String id){
        Call<VoucherDto> call = ApiClient.getServiceClient(VoucherServices.class).Get(id);
        call.enqueue(new Callback<VoucherDto>() {
            @Override
            public void onResponse(Call<VoucherDto> call, Response<VoucherDto> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth vouchers with status code " + code);
                    return;
                }
                VoucherDto responseBody = response.body();
                selected = responseBody;
                binding.textView9.setText("Voucher selected: "+selected.VoucherCode + " "+ selected.Percentage+"% OFF");
            }

            @Override
            public void onFailure(Call<VoucherDto> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth voucher");
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}