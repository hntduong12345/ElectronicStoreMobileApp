package com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.FragmentProductDetailBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.example.electronicstoremobileapp.models.CartList;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    FragmentProductDetailBinding binding;
    public NavController navController;

    ProductDto product;
    Context currentContext;
    List<Cart> cart;

    CartList localCartList;
    SharedPreferences sharedPreferences;
    Gson gson = new Gson();


    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProductDto productDto = (ProductDto) getArguments().get("ProductDetail");
            this.product = productDto;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("CartData", Context.MODE_PRIVATE);
        currentContext = this.currentContext;

        BindToView();

        fetchCartData();

        if (checkIsAddToCart()) {
            binding.buttonAddToCartPDP.setEnabled(false);
            binding.buttonAddToCartPDP.setText("Added");
        } else {
            binding.buttonAddToCartPDP.setEnabled(true);
            binding.buttonAddToCartPDP.setText("Add to cart");
        }

        return view;
    }

    private void fetchCartData() {
        cart = new ArrayList<>();
        cart.clear();
        Gson gson = new Gson();
        String dataJson = sharedPreferences.getString("CartObject", "");
        if (!TextUtils.equals(dataJson, "[]") && !TextUtils.isEmpty(dataJson)) {
            Type listType = new TypeToken<List<Cart>>(){}.getType();
            cart = gson.fromJson(dataJson, listType);
        }
    }

    private boolean checkIsAddToCart() {
        return cart.stream().map(cart1 -> cart1.cartItem).filter(p -> TextUtils.equals(p.ProductId, product.ProductId)).findAny().isPresent();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonAddToCartPDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.add(new Cart(product, 1));

                Gson gson = new Gson();

                String jsonData = gson.toJson(cart);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("CartObject", jsonData);
                editor.apply();

                binding.buttonAddToCartPDP.setEnabled(false);
                binding.buttonAddToCartPDP.setText("Added");
            }
        });
    }

    private void BindToView() {
        binding.textViewProductDetailName.setText(product.ProductName);
        binding.textViewProductDetailDescription.setText(product.Description);
        binding.textViewProductDetailPrice.setText(String.valueOf(product.DefaultPrice));
        binding.textViewProductDetailManufacturer.setText(product.Manufacturer);

        binding.imageViewProductDetailImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //binding.imgProductImage.setImageURI(Uri.parse(getProduct.RelativeUrl));
        Picasso.get()
                .load(product.RelativeUrl)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(binding.imageViewProductDetailImg);
    }
}