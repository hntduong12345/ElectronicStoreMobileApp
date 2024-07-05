package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartPageFragment extends Fragment {

    FragmentCartPageBinding binding;
    public NavController navController;

    public CartPageFragment() {
        // Required empty public constructor
    }


    public static CartPageFragment newInstance() {
        CartPageFragment fragment = new CartPageFragment();
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
        binding = FragmentCartPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return inflater.inflate(R.layout.fragment_cart_page, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}