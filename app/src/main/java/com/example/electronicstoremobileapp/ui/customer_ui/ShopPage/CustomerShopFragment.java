package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerCartBinding;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerShopBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CustomerCartFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerShopFragment extends Fragment {

    FragmentCustomerShopBinding binding;
    public NavController navController;

    public CustomerShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerShopFragment newInstance(String param1, String param2) {
        CustomerShopFragment fragment = new CustomerShopFragment();
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
        binding = FragmentCustomerShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_customer_shop);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.customerShopFragment);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}