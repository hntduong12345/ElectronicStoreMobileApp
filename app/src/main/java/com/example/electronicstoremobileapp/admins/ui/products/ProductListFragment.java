package com.example.electronicstoremobileapp.admins.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.viewElements.ProductListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductListBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.PagingResponseDto;
import com.example.electronicstoremobileapp.models.ProductDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {
    private static final String CATEGORY_ALL = "ALL";
    private FragmentAdminProductListBinding binding;
    List<ProductDto> productDtoList;
    ProductListViewAdapter productAdapter;
    Toolbar toolbar;
    List<CategoryDto> categoryDtoListDropdown;
    Spinner dropdownCategories;
    NavHostFragment parentFragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminProductListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productDtoList = new ArrayList<ProductDto>();
        categoryDtoListDropdown = new ArrayList<>();

        dropdownCategories = binding.dropdownCategoriesSelection;
        ArrayAdapter<CategoryDto> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, categoryDtoListDropdown);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dropdownCategories.setAdapter(adapter);
        parentFragment = (NavHostFragment) getParentFragment();
        dropdownCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropdownCategories.setSelection(position);
                dropdownCategoriesClick(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_product_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });


        fecthData();
        fetchCategories();
        productAdapter = new ProductListViewAdapter(this.getContext(), productDtoList, R.layout.listviewitem_admin_product_list_item,parentFragment);
        binding.fragmentProductListView.setAdapter(productAdapter);

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void fecthData() {
        Call<List<ProductDto>> call = ApiClient.getServiceClient(ProductServices.class).GetAll();
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                List<ProductDto> responseBody = response.body();
                productDtoList.clear();
                productDtoList.addAll(responseBody);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth products");
                return;
            }
        });
    }
    private void fetchCategories() {
        Call<List<CategoryDto>> getCategories = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        getCategories.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                    return;
                }
                categoryDtoListDropdown.clear();
                categoryDtoListDropdown.add(new CategoryDto(CATEGORY_ALL,"All","All"));
                categoryDtoListDropdown.addAll(response.body());
                ArrayAdapter<CategoryDto> adapter = (ArrayAdapter<CategoryDto>) dropdownCategories.getAdapter();
                adapter.notifyDataSetChanged();
                dropdownCategories.setSelection(0);
                CategoryDto getSelectedItem = (CategoryDto) dropdownCategories.getSelectedItem();
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                return;
            }
        });
    }
    private void fetchProductInCategory(String categoryId){
        Call<PagingResponseDto<ProductDto>> call = ApiClient.getServiceClient(ProductServices.class).GetProductInCategoryId(0,1000000,categoryId);
        call.enqueue(new Callback<PagingResponseDto<ProductDto>>() {
            @Override
            public void onResponse(Call<PagingResponseDto<ProductDto>> call, Response<PagingResponseDto<ProductDto>> response) {
                if (!response.isSuccessful()) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + response.code());
                    return;
                }
                PagingResponseDto<ProductDto> responseBody = response.body();
                productDtoList.clear();
                productDtoList.addAll(responseBody.Values);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PagingResponseDto<ProductDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
            }
        });
    }
    private boolean menuItemClicked(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.toolbarProductCreate) {
            Log.println(Log.WARN, "CLICK", "Create is clicked in product");
            NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
            if (parentFragment != null) {
                NavController navController = parentFragment.getNavController();
                navController.navigate(R.id.action_navigation_product_list_to_navigation_product_create);
            }
//            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
//            navController.navigate(R.id.action_navigation_product_list_to_navigation_product_create);
            return true;
        }
        return false;
    }

    private void dropdownCategoriesClick(AdapterView<?> parent, View view, int position, long id) {
        CategoryDto selectedDto =(CategoryDto) dropdownCategories.getSelectedItem();
        if(selectedDto.CategoryId == CATEGORY_ALL){
            fecthData();
            return;
        }else{
            fetchProductInCategory(selectedDto.CategoryId);
            return;
        }
    }


}