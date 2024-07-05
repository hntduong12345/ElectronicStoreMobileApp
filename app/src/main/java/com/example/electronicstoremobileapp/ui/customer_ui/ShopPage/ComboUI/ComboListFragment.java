package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ComboUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentCartPageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentComboListBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CartPageFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComboListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComboListFragment extends Fragment {

    FragmentComboListBinding binding;
    public NavController navController;

    public ComboListFragment() {
        // Required empty public constructor
    }


    public static ComboListFragment newInstance() {
        ComboListFragment fragment = new ComboListFragment();
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
        binding = FragmentComboListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return inflater.inflate(R.layout.fragment_combo_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}