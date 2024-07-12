package com.example.electronicstoremobileapp.admins.ui.products;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.utils.ProductHelpers;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductUpdateSaleBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductUpdateStorageBinding;
import com.example.electronicstoremobileapp.models.ProductDto;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductUpdateStorageFragment extends Fragment {
    ProductDto selectedProduct;
    Toolbar updateToolbar;
    FragmentAdminProductUpdateStorageBinding binding;
    int TOBE_UPDATED_AMOUNT = 0;
    public ProductUpdateStorageFragment() {
        // Required empty public constructor
    }

    public static ProductUpdateSaleFragment newInstance() {
        ProductUpdateSaleFragment fragment = new ProductUpdateSaleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProductDto getProduct = (ProductDto) getArguments().get(AppConstant.TOBE_UPDATE_OBJECT_KEY);
            if (getProduct == null) {
                Toast.makeText(this.getContext(), "CANNOT FOUND PRODUCT TO UPDATE", Toast.LENGTH_LONG);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
            }
            selectedProduct = getProduct;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProductUpdateStorageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        updateToolbar = binding.toolbar;
        updateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarNavClick(v);
            }
        });
        setToModel();
        binding.btnUpdateStorageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateStorageclicked();
            }
        });
        binding.btnMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOBE_UPDATED_AMOUNT -= 1;
                if(TOBE_UPDATED_AMOUNT < 0){
                    TOBE_UPDATED_AMOUNT = 0;
                }
                binding.edtAmount.setText(String.valueOf(TOBE_UPDATED_AMOUNT));
            }
        });
        binding.btnPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOBE_UPDATED_AMOUNT += 1;
                binding.edtAmount.setText(String.valueOf(TOBE_UPDATED_AMOUNT));
            }
        });
        binding.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int amount = 0;
                String getAmountString = binding.edtAmount.getText().toString().trim();
                if(StringUtils.isEmpty(getAmountString)){
                    getAmountString = "0";
                }
                amount = Integer.valueOf(getAmountString);
                if(amount < 0){
                    amount = 0;
                }
                TOBE_UPDATED_AMOUNT = amount;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void onUpdateStorageclicked() {
        boolean validResult = validateParam();
        if(validResult == false){
            return;
        }

        RequestBody amount = RequestBody.create(MultipartBody.FORM, String.valueOf(TOBE_UPDATED_AMOUNT));
        Call<Void> updateStorage = ApiClient.getServiceClient(ProductServices.class)
                .UpdateStorage(selectedProduct.ProductId, amount);
        updateStorage.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                    parentFragment.getNavController().navigate(R.id.action_navigation_product_update_storage_to_navigation_product_list);
                }
                Log.e("ERROR update","Error update fail "+ response.code());
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("ERROR update","Error update", throwable);
            }
        });
    }
    private void setToModel(){
        binding.txtProductId.setText(selectedProduct.ProductId);
        binding.txtProductName.setText(selectedProduct.ProductName);
        TOBE_UPDATED_AMOUNT = selectedProduct.StorageAmount;
        binding.edtAmount.setText(String.valueOf(TOBE_UPDATED_AMOUNT));

    }
    @SuppressWarnings({  })
    private boolean validateParam() {
        boolean isValid = true;
        int tryGetvalue = Integer.valueOf( binding.edtAmount.getText().toString().trim());
        if(tryGetvalue < 0 || tryGetvalue > AppConstant.MAX_STORAGE_CAPACITY){
            binding.edtAmount.setError("amount must > 0 and < " + AppConstant.MAX_STORAGE_CAPACITY);
            isValid = false;
        }
        TOBE_UPDATED_AMOUNT = tryGetvalue;
        return isValid;
    }

    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
           // navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
            navController.navigateUp();
        }
    }



}