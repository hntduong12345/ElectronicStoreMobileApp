package com.example.electronicstoremobileapp.ui.customer_ui.UserPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.JwtUtil;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerHomeBinding;
import com.example.electronicstoremobileapp.databinding.FragmentCustomerUserBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.CustomerHomeFragment;

public class CustomerUserFragment extends Fragment {

    FragmentCustomerUserBinding binding;
    public NavController navController;
/*
    public CustomerUserFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerHomeFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static CustomerUserFragment newInstance(String param1, String param2) {
        CustomerUserFragment fragment = new CustomerUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_customer_user);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.userPageFragment);
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