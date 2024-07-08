package com.example.electronicstoremobileapp.ui.customer_ui.UserPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentUserOrdersListBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOrdersList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrdersList extends Fragment {

    FragmentUserOrdersListBinding binding;
    public NavController navController;

    public UserOrdersList() {
        // Required empty public constructor
    }


    public static UserOrdersList newInstance() {
        UserOrdersList fragment = new UserOrdersList();
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
        binding = FragmentUserOrdersListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return inflater.inflate(R.layout.fragment_user_orders_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}