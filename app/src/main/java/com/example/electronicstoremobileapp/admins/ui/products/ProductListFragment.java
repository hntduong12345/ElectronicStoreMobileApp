package com.example.electronicstoremobileapp.admins.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.viewElements.ProductListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductListBinding;
import com.example.electronicstoremobileapp.models.ProductDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    private FragmentAdminProductListBinding binding;
    List<ProductDto> productDtoList;
    ProductListViewAdapter productAdapter;
    Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminProductListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_product_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });

        productDtoList = new ArrayList<ProductDto>();
        fecthData();
        productAdapter = new ProductListViewAdapter(this.getContext(), productDtoList, R.layout.listviewitem_admin_product_list_item);
        binding.fragmentProductListView.setAdapter(productAdapter);

        return root;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}