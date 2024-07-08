package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.electronicstoremobileapp.Adapters.category.CategoryAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.accounts.viewElements.AccountListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentShopPageBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CartPageFragment;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomeActivity;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopPageFragment extends Fragment {

    FragmentShopPageBinding binding;

    List<CategoryDto> categoryList;
    CategoryAdapter categoryAdapter;
    NavHostFragment parentFragment;
    Context currentContext;

    public ShopPageFragment() {
        // Required empty public constructor
    }


    public static ShopPageFragment newInstance() {
        ShopPageFragment fragment = new ShopPageFragment();
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
        binding = FragmentShopPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        currentContext = this.getContext();

        this.parentFragment = (NavHostFragment) getParentFragment();
        categoryList = new ArrayList<>();
        featchAllCategory();

        return view;
//        return inflater.inflate(R.layout.fragment_shop_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryDto category = categoryList.get(position);

                if (parentFragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("CategoryId", category.CategoryId);
                    bundle.putString("CategoryName", category.CategoryName);
                    parentFragment.getNavController().navigate(R.id.action_shopPageFragment_to_shopProductListFragment, bundle);
                }
            }
        });
    }

    private void featchAllCategory(){
        Call<List<CategoryDto>> call = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        featchData(call);
    }

    private  void featchData(Call<List<CategoryDto>> call){
        call.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth categories with status code " + code);
                    categoryAdapter.notifyDataSetChanged();
                    return;
                }
                List<CategoryDto> responseBody = response.body();
                categoryList.clear();
                categoryList.addAll(responseBody);
                categoryAdapter = new CategoryAdapter(currentContext, R.layout.component_category, categoryList, parentFragment);
                binding.listViewCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
            }
        });
    }
}