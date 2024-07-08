package com.example.electronicstoremobileapp.ui.customer_ui.HomePage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.electronicstoremobileapp.Adapters.product.ProductAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.ProductDto;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageFragment extends Fragment {

    FragmentHomePageBinding binding;
    public NavController navController;

    ArrayList<ProductDto> featureProducts;
    ArrayList<ProductDto> newProducts;
    ProductAdapter featureProductAdapter, newProductAdapter;

    Context currentContext;
    NavHostFragment parentFragment;

    public HomePageFragment() {
        // Required empty public constructor
    }


    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
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
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.parentFragment = (NavHostFragment) getParentFragment();

        currentContext = this.getContext();
        featureProducts = new ArrayList<>();
//        newProducts = new ArrayList<>();

        fecthData();

    return view;
    }

    private void fecthData(){
        Call<CategoryDto> call = ApiClient.getServiceClient(ProductServices.class).GetRange(1,5);
        call.enqueue(new Callback<CategoryDto>() {
            @Override
            public void onResponse(Call<CategoryDto> call, Response<CategoryDto> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                CategoryDto responseBody = response.body();
                featureProducts.clear();
                featureProducts.addAll(responseBody.Values);
                featureProductAdapter = new ProductAdapter(currentContext, featureProducts, R.layout.component_product_view);
                binding.listViewFeatureProduct.setAdapter(featureProductAdapter);
                featureProductAdapter.notifyDataSetChanged();

//                newProducts.clear();
//                newProducts.addAll(responseBody.Values);
//                newProductAdapter = new ProductAdapter(currentContext, newProducts, R.layout.component_product_view);
//                binding.listViewNewProduct.setAdapter(newProductAdapter);
//                newProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CategoryDto> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth products");
                return;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.listViewFeatureProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parentFragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ProductDetail", featureProducts.get(position));
                    parentFragment.getNavController().navigate(R.id.action_homePageFragment_to_productDetailFragment, bundle);
                }
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