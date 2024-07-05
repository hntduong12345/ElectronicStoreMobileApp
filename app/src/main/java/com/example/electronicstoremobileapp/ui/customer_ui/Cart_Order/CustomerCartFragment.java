package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerCartBinding;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerHomeBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.CustomerHomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerCartFragment extends Fragment {

    FragmentCustomerCartBinding binding;
    public NavController navController;

    public CustomerCartFragment() {
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
    public static CustomerCartFragment newInstance(String param1, String param2) {
        CustomerCartFragment fragment = new CustomerCartFragment();
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
        binding = FragmentCustomerCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_customer_cart);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.customerCartFragment);
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