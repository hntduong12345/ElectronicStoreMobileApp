package com.example.electronicstoremobileapp.admins.ui.products.viewElements;

import static com.example.electronicstoremobileapp.AppConstant.TOBE_UPDATE_OBJECT_KEY;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminProductListItemBinding;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListViewAdapter extends BaseAdapter {

    Context parentContext;
    List<ProductDto> productLists;
    int layoutId;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;
    public ProductListViewAdapter(Context parentContext, List<ProductDto> productLists, int layoutId, NavHostFragment parentNavigationFragment) {
        this.parentContext = parentContext;
        this.productLists = productLists;
        this.layoutId = layoutId;
        this.layoutInflater = LayoutInflater.from(parentContext);
        this.parentNavigationFragment = parentNavigationFragment;
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
        ProductDto getProduct = productLists.get(position);
        String productId = getProduct.ProductId;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminProductListItemBinding binding;
        if (convertView == null) {
            binding = ListviewitemAdminProductListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ListviewitemAdminProductListItemBinding) convertView.getTag();
        }
        ProductDto getProduct = (ProductDto) getItem(position);
        binding.txtProductName.setText(getProduct.ProductName);
        binding.txtProductDescription.setText(getProduct.Description);
        binding.txtProductPrice.setText(String.valueOf(getProduct.DefaultPrice));
        binding.imgProductImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //binding.imgProductImage.setImageURI(Uri.parse(getProduct.RelativeUrl));
        Picasso.get()
                .load(getProduct.RelativeUrl)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(binding.imgProductImage);
        binding.btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.WARN, "UPDATE", "UPDATE pressed");
                Bundle bundle = new Bundle();
                bundle.putParcelable(TOBE_UPDATE_OBJECT_KEY,getProduct);
                parentNavigationFragment.getNavController().navigate(R.id.action_navigation_product_list_to_navigation_product_update,bundle);
            }
        });

        binding.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.WARN, "DELETE", "DELETE pressed");
            }
        });
        return convertView;
    }

}
