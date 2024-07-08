package com.example.electronicstoremobileapp.Adapters.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ComponentCartItemRowBinding;
import com.example.electronicstoremobileapp.databinding.ComponentProductViewBinding;
import com.example.electronicstoremobileapp.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    Context parentContext;
    List<Cart> cartItemLists;
    int layoutId;
    LayoutInflater layoutInflater;

    public CartAdapter(Context parentContext, List<Cart> cartItemLists, int layoutId) {
        this.parentContext = parentContext;
        this.cartItemLists = cartItemLists;
        this.layoutId = layoutId;
        this.layoutInflater = LayoutInflater.from(parentContext);
    }

    @Override
    public int getCount() {
        return cartItemLists.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComponentCartItemRowBinding binding;

        if (convertView == null) {
            binding = ComponentCartItemRowBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ComponentCartItemRowBinding) convertView.getTag();
        }

        Cart getCartItem = (Cart) cartItemLists.get(position);

        binding.textViewProductNameCart.setText(getCartItem.cartItem.ProductName);
        binding.textViewProductPriceCart.setText(String.valueOf(getCartItem.cartItem.DefaultPrice));
        binding.textViewQuantity.setText(String.valueOf(getCartItem.quantity));
        binding.imageViewProductCart.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //binding.imgProductImage.setImageURI(Uri.parse(getProduct.RelativeUrl));
        Picasso.get()
                .load(getCartItem.cartItem.RelativeUrl)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(binding.imageViewProductCart);

        binding.imageViewMinusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCartItem.quantity -1 <= 0 ){
                    Toast.makeText(parentContext, "Quantity cannot 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    getCartItem.ChangeQuantity(-1);
                    binding.textViewQuantity.setText(String.valueOf(getCartItem.quantity));
                }
            }
        });

        binding.imageViewPlusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCartItem.cartItem.StorageAmount < getCartItem.quantity + 1){
                    Toast.makeText(parentContext, "Quantity is exceed the current product storage amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    getCartItem.ChangeQuantity(1);
                    binding.textViewQuantity.setText(String.valueOf(getCartItem.quantity));
                }
            }
        });

        binding.imageViewRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //Optional click on quantity text to show out popup to insert quantity

        return convertView;
    }
}
