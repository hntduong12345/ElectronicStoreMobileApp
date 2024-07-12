package com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentPaymentResultBinding;
import com.example.electronicstoremobileapp.databinding.FragmentSubmitOrderBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitOrderFragment extends Fragment {

    FragmentSubmitOrderBinding binding;
    public NavController navController;

    public SubmitOrderFragment() {
        // Required empty public constructor
    }


    public static SubmitOrderFragment newInstance() {
        SubmitOrderFragment fragment = new SubmitOrderFragment();
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
        binding = FragmentSubmitOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        WebView webView = binding.WebViewVNP.findViewById(R.id.WebViewVNP);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.loadUrl("https://lol.fandom.com/");
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://lol.fandom.com/")));

        return inflater.inflate(R.layout.fragment_submit_order, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}