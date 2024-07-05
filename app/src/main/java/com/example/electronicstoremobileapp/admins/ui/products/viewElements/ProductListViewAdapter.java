package com.example.electronicstoremobileapp.admins.ui.products.viewElements;

import static com.example.electronicstoremobileapp.AppConstant.TOBE_UPDATE_OBJECT_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.DialogAdminProductDeleteBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminProductListItemBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                View dialogView = layoutInflater.inflate(R.layout.dialog_admin_product_delete, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
                builder.setView(dialogView);
                Button cancelButton = dialogView.findViewById(R.id.btnCancel);
                Button deleteButton = dialogView.findViewById(R.id.btnDelete);
                TextView productName = dialogView.findViewById(R.id.txtProductName);
                productName.setText(getProduct.ProductName);
                final AlertDialog dialog = builder.create();
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the delete action
                        dialogDeleteClick(v,getProduct);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }
    private void dialogDeleteClick(View view,ProductDto productDto) {
        Toast.makeText(parentContext, "delete product with id + " + productDto.ProductId, Toast.LENGTH_SHORT).show();
        Call deleteProduct = ApiClient.getServiceClient(ProductServices.class).Delete(productDto.ProductId);
        deleteProduct.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(parentContext,"DELETE PRODUCT SUCCESS", Toast.LENGTH_SHORT).show();
                    productLists.remove(productDto);
                    notifyDataSetChanged();
                }
                else{
                    Log.e("DELETE PRODUCT ERR", "fail to delte on server");
                    Toast.makeText(parentContext,"DELETE PRODUCT ERROR from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("DELETE PRODUCT ERR", throwable.getMessage(),throwable);
                Toast.makeText(parentContext,"DELETE PRODUCT ERROR from client", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
