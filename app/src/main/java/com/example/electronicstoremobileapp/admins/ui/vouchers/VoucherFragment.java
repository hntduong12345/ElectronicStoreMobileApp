package com.example.electronicstoremobileapp.admins.ui.vouchers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherBinding;

public class VoucherFragment extends Fragment {
    FragmentAdminVoucherBinding binding;
    NavController navController;

    public VoucherFragment() {

    }

    public static VoucherFragment newInstance() {
        VoucherFragment fragment = new VoucherFragment();
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
        /*binding = FragmentAdminVoucherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_voucher);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.navigation_voucher_list);
        return view;*/
        binding = FragmentAdminVoucherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_voucher);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.navigation_voucher_list);
        return view;
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }

}
