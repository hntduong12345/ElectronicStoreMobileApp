package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.electronicstoremobileapp.Adapters.product.ProductAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.viewElements.ProductListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentShopProductListBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CartPageFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopProductListFragment extends Fragment {

    FragmentShopProductListBinding binding;
    public NavController navController;

    List<ProductDto> productList;
    ProductAdapter productAdapter;
    Context currentContext;
    String categoryId, cateName;

    public ShopProductListFragment() {
        // Required empty public constructor
    }


    public static ShopProductListFragment newInstance() {
        ShopProductListFragment fragment = new ShopProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cateName = (String) getArguments().get("CategoryName");
            categoryId = (String) getArguments().get("CategoryId");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopProductListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        currentContext = this.getContext();
        binding.textViewCateName.setText(cateName);


        productList = new ArrayList<ProductDto>();
        fecthData();

        return inflater.inflate(R.layout.fragment_shop_product_list, container, false);
    }

    private void fecthData(){
        Call<List<ProductDto>> call = ApiClient.getServiceClient(ProductServices.class).GetProductbyCategory(categoryId,0,50);
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                List<ProductDto> responseBody = response.body();
                productList.clear();
                productList.addAll(responseBody);
                productAdapter = new ProductAdapter(currentContext, productList, R.layout.listviewitem_admin_product_list_item);
                binding.listViewCategoryProduct.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth products");
                return;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}