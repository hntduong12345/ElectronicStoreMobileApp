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
import com.example.electronicstoremobileapp.databinding.FragmentAdminProfileDetailBinding;

public class ProfileDetailFragment extends Fragment {
    FragmentAdminProfileDetailBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminProfileDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        NavController navController = parentFragment.getNavController();
        binding.BtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_profile_detail_to_order_list);
            }
        });
        binding.BtnRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_profile_detail_to_revenue);
            }
        });
        binding.BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoggingUtil.LogOut(getContext());
                getActivity().finish();
            }
        });
        return view;
    }
}
