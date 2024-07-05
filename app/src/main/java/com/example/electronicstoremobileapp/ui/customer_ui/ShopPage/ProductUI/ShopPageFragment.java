package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.electronicstoremobileapp.Adapters.category.CategoryAdapter;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentShopPageBinding;
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
    public NavController navController;

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

        return inflater.inflate(R.layout.fragment_shop_page, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}