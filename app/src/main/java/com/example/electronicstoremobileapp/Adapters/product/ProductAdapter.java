package com.example.electronicstoremobileapp.Adapters.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ComponentProductViewBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminProductListItemBinding;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    Context parentContext;
    List<ProductDto> productLists;
    int layoutId;
    LayoutInflater layoutInflater;

    public ProductAdapter(Context parentContext, List<ProductDto> productLists, int layoutId) {
        this.parentContext = parentContext;
        this.productLists = productLists;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return productLists.size();
    }

    @Override
    public Object getItem(int position) {
        return productLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComponentProductViewBinding binding;

        if (convertView == null) {
            binding = ComponentProductViewBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ComponentProductViewBinding) convertView.getTag();
        }

        ProductDto getProduct = (ProductDto) getItem(position);
        binding.textViewProductName.setText(getProduct.ProductName);
        binding.textViewProductPrice.setText(String.valueOf(getProduct.DefaultPrice));
        binding.imageViewProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //binding.imgProductImage.setImageURI(Uri.parse(getProduct.RelativeUrl));
        Picasso.get()
                .load(getProduct.RelativeUrl)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(binding.imageViewProduct);

        return convertView;
    }
}
