package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentPaymentResultBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentResultFragment extends Fragment {

    FragmentPaymentResultBinding binding;
    public NavController navController;

    public PaymentResultFragment() {
        // Required empty public constructor
    }


    public static PaymentResultFragment newInstance() {
        PaymentResultFragment fragment = new PaymentResultFragment();
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
        binding = FragmentPaymentResultBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return inflater.inflate(R.layout.fragment_payment_result, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}