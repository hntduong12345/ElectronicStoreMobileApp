package com.example.electronicstoremobileapp.admins.ui.products;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.utils.ProductHelpers;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductUpdateSaleBinding;
import com.example.electronicstoremobileapp.models.ProductDto;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;


public class ProductUpdateSaleFragment extends Fragment {
    ProductDto selectedProduct;
    Toolbar updateToolbar;
    FragmentAdminProductUpdateSaleBinding binding;

    public ProductUpdateSaleFragment() {
        // Required empty public constructor
    }

    public static ProductUpdateSaleFragment newInstance(String param1, String param2) {
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
        binding = FragmentAdminProductUpdateSaleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        updateToolbar = binding.toolbar;
        updateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarNavClick(v);
            }
        });
        binding.edtDateSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDatePickerClick();;
            }
        });
        binding.edtTimeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimePickerClicked();;
            }
        });
        binding.btnUpdateSaleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateSaleClicked();
            }
        });

        return view;
    }

    private void onUpdateSaleClicked() {
        boolean validResult = validateParam();
        if(validResult == false){
            return;
        }

    }
    @SuppressWarnings({  })
    private boolean validateParam() {
        boolean isValid = true;
        String getDate = binding.edtDateSale.getText().toString();
        String getTime = binding.edtTimeSale.getText().toString();
        if(StringUtils.isAnyBlank(getDate)){
            binding.edtDateSale.setError("must fill date");
            isValid = false;
        }
        if(StringUtils.isAnyBlank(getTime)){
            binding.edtTimeSale.setError("must fill time, only count minute");
            isValid =false;
        }
        if(ProductHelpers.isDateTimeAfterOrEqualNow_WithSpanHour(
                ProductHelpers.parseFromTimeString(getTime),
                ProductHelpers.parseFromDateString(getDate),
                1) == false){
            binding.edtDateSale.setError("must fill date after now");
            binding.edtTimeSale.setError("must fill time, only count minute, after now");
            isValid =false;
        }
        return false;
    }

    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
        }
    }
    private void onDatePickerClick(){
        String tryGetEdtDateTime = binding.edtDateSale.getText().toString();
        DatePickerDialog dialog ;
        if(!StringUtils.isBlank(tryGetEdtDateTime)){
            LocalDate parseLocalDate = ProductHelpers.parseFromDateString(tryGetEdtDateTime);
            dialog = new DatePickerDialog(getContext(),null,parseLocalDate.getYear(),parseLocalDate.getMonthValue(),parseLocalDate.getDayOfMonth());
        }else{
            dialog = new DatePickerDialog(getContext());
        }
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateString = ProductHelpers.getDateFromParam(year,month,dayOfMonth);
                binding.edtDateSale.setText(dateString);
            }
        });
        dialog.show();
    }
    private void onTimePickerClicked(){
        String tryGetEdtTime = binding.edtTimeSale.getText().toString();
        TimePickerDialog dialog ;
        if(!StringUtils.isBlank(tryGetEdtTime)){
            LocalTime parseLocalTime = ProductHelpers.parseFromTimeString(tryGetEdtTime);
            dialog = new TimePickerDialog(getContext(),this::onTimeSetEvent,parseLocalTime.getHour(),0,true);
        }else{
            dialog = new TimePickerDialog(getContext(),this::onTimeSetEvent,0,0,true);
        }
        dialog.show();
    }
    public void onTimeSetEvent(TimePicker view, int hourOfDay, int minute) {
        String timeString = ProductHelpers.getTimeFromParam(hourOfDay,minute);
        binding.edtDateSale.setText(timeString);
    }
}