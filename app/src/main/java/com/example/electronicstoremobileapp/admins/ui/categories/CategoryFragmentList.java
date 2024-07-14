package com.example.electronicstoremobileapp.admins.ui.categories;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.categories.viewElements.CategoryListViewAdapter;
import com.example.electronicstoremobileapp.admins.ui.products.viewElements.ProductListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminCategoryListBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductListBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.ProductDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragmentList extends Fragment {
    FragmentAdminCategoryListBinding binding;
    CategoryListViewAdapter categoryListViewAdapter;
    Toolbar toolbar;
    List<CategoryDto> categoryDtoList;
    NavHostFragment parentFragment;


    public CategoryFragmentList() {
        // Required empty public constructor
    }

    public static CategoryFragmentList newInstance(String param1, String param2) {
        CategoryFragmentList fragment = new CategoryFragmentList();
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
        // Inflate the layout for this fragment
        binding = FragmentAdminCategoryListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryDtoList = new ArrayList<>();
        parentFragment = (NavHostFragment) getParentFragment();
        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_product_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });
        categoryListViewAdapter = new CategoryListViewAdapter(this.getContext(), categoryDtoList, R.layout.listviewitem_admin_category_list_item,getLayoutInflater(),parentFragment);
        binding.fragmentCategoryListView.setAdapter(categoryListViewAdapter);
        fecthCategoryData();
        return root;
    }
    private boolean menuItemClicked(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.toolbarProductCreate) {
            Log.println(Log.WARN, "CLICK", "Create is clicked in category");
            NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
            if (parentFragment != null) {
                NavController navController = parentFragment.getNavController();
                navController.navigate(R.id.action_navigation_category_list_to_navigation_category_create);
            }
            return true;
        }
        return false;
    }
    private void fecthCategoryData() {
        Call<List<CategoryDto>> call = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        call.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth category with status code " + code);
                    return;
                }
                List<CategoryDto> responseBody = response.body();
                categoryDtoList.clear();
                categoryDtoList.addAll(responseBody);
                categoryListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth category");
                return;
            }
        });
    }
}