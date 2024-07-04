package com.example.electronicstoremobileapp.admins.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductBinding;

public class ProductFragment extends Fragment {

    FragmentAdminProductBinding binding;
    NavController navController;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        binding = FragmentAdminProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_product);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.navigation_product_list);
        return view;
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
//    private void changeFragment(Fragment fragmentToGo) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction =
//                fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.frameLayoutContainer, fragmentToGo);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}