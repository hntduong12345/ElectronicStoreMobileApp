package com.example.electronicstoremobileapp.admins.ui.products;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.models.UpdateProductDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductUpdateBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.products.ProductDto;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductUpdateFragment extends Fragment {
    FragmentAdminProductUpdateBinding binding;
    UpdateProductDto updateProductDto;
    ProductDto selectedProduct;
    Spinner dropdownCategory;
    List<CategoryDto> categoryDtoList;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProductUpdateFragment() {
        // Required empty public constructor
        updateProductDto = new UpdateProductDto();
        LocalDateTime now = Helpers.getLocalDateTime();
        year = now.getYear();
        month = now.getMonth().getValue();
        day = now.getDayOfMonth();
        hour = now.getHour();
        minute = now.getMinute();
        second = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ProductUpdateFragment newInstance(String param1, String param2) {
        ProductUpdateFragment fragment = new ProductUpdateFragment();
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
        binding = FragmentAdminProductUpdateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dropdownCategory = binding.dropdownCategories;
        binding.btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTimeClick(v);
            }
        });
        binding.btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateClick(v);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_navigation_product_create_to_navigation_product);
                }
//                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
//                navController.navigate(R.id.action_navigation_product_create_to_navigation_product);
            }
        });
        fetchData();
        return root;
    }

    private void chooseDateClick(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setYearMonDate(year, month, day);
            }
        }, year, month, day);
    }

    private void chooseTimeClick(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTime(hourOfDay, minute, 0);
            }
        }, hour, minute, true);
    }

    private void setYearMonDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    private void fetchData() {
        Call<List<CategoryDto>> getCategories = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        getCategories.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                    return;
                }
                categoryDtoList.clear();
                categoryDtoList.addAll(response.body());
                ArrayAdapter<CategoryDto> adapter = (ArrayAdapter<CategoryDto>) dropdownCategory.getAdapter();
                adapter.notifyDataSetChanged();
                dropdownCategory.setSelection(0);
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                return;
            }
        });
    }

    private void bindProductToField() {

    }
}