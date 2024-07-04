package com.example.electronicstoremobileapp.admins.ui.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentAdminAccountBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    FragmentAdminAccountBinding binding;
    public NavController navController;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentAdminAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(com.example.electronicstoremobileapp.R.id.nav_host_fragment_account);
        NavController navController = navHostFragment.getNavController();
        this.navController = navController;
        navigateToFragment(R.id.navigation_account_list);
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