package com.example.electronicstoremobileapp.admins.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.admins.ui.vouchers.VoucherFragment;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProfileBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProfileDetailBinding;

public class ProfileFragment extends Fragment {
    FragmentAdminProfileBinding binding;
    NavController navController;

    public ProfileFragment(){

    }
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_profile);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navController.navigate(R.id.navigation_profile_detail);
        return view;
    }
}
