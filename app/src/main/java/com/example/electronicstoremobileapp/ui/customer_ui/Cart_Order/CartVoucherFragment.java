package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.Adapters.cart.CartAdapter;
import com.example.electronicstoremobileapp.Adapters.cart.CartVoucherAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.vouchers.viewElements.VoucherAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerCartVoucherBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.example.electronicstoremobileapp.models.CartList;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartVoucherFragment extends Fragment {
    FragmentCustomerCartVoucherBinding binding;
    public NavController navController;

    List<VoucherDto> voucherList;
    CartVoucherAdapter voucherAdapter;
    SharedPreferences sharedPreferences;
    Context currentContext;
    Toolbar toolbar;
    String dateFormat = "dd/MM/yyyy HH:mm";
    SimpleDateFormat sdf;
    public CartVoucherFragment() {
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
        binding = FragmentCustomerCartVoucherBinding.inflate(inflater, container, false);
        sdf = new SimpleDateFormat(dateFormat);
        View view = binding.getRoot();
        currentContext = this.getContext();

        voucherList = new ArrayList<>();
        voucherAdapter = new CartVoucherAdapter(this.getContext(), voucherList, (NavHostFragment) getParentFragment());
        GetVouchers();
        toolbar = binding.fragmentToolbar;
        toolbar.setTitle("Voucher Details");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    NavOptions option = new NavOptions.Builder()
                            .setEnterAnim(R.anim.enter_from_left)
                            .setExitAnim(R.anim.exit_to_right)
                            .build();

                    navController.navigate(R.id.action_cartVoucherFragment_to_cartPageFragment,null,option);
                }
            }
        });
        binding.listViewListVoucher.setAdapter(voucherAdapter);
        return view;
    }
    void GetVouchers(){
        Call<List<VoucherDto>> call = ApiClient.getServiceClient(VoucherServices.class).GetAll();
        call.enqueue(new Callback<List<VoucherDto>>() {
            @Override
            public void onResponse(Call<List<VoucherDto>> call, Response<List<VoucherDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                List<VoucherDto> responseBody = response.body();
                voucherList.clear();
                Date now = new Date();
                for(VoucherDto voucher : responseBody){
                    try {
                        Date expiry = sdf.parse(voucher.ExpiryDate);
                        if(!expiry.before(now)){
                            voucherList.add(voucher);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(voucherList.size()==0){
                    binding.textView3.setText("No Available Voucher");
                }
                voucherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VoucherDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth voucher");
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
}
