package com.example.electronicstoremobileapp.admins.ui.categories.viewElements;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminAccountListItemBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminCategoryListItemBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.CategoryDto;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListViewAdapter extends BaseAdapter {

    Context parentContext;
    List<CategoryDto> categoryDtoList;
    int layoutId;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;

    public CategoryListViewAdapter(Context parentContext, List<CategoryDto> categoryDtoList, int layoutId, LayoutInflater layoutInflater, NavHostFragment parentNavigationFragment) {
        this.parentContext = parentContext;
        this.categoryDtoList = categoryDtoList;
        this.layoutId = layoutId;
        this.layoutInflater = layoutInflater;
        this.parentNavigationFragment = parentNavigationFragment;
    }

    @Override
    public int getCount() {
        return categoryDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminCategoryListItemBinding binding;
        if (convertView == null) {
            binding = ListviewitemAdminCategoryListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ListviewitemAdminCategoryListItemBinding) convertView.getTag();
        }
        CategoryDto getCategory = (CategoryDto) getItem(position);
        binding.txtCategoryId.setText(getCategory.CategoryId);
        binding.txtCategoryName.setText(getCategory.CategoryName);
        binding.txtCategoryDescription.setText(getCategory.CategoryDescription);
        binding.btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.TOBE_UPDATE_OBJECT_KEY,getCategory);
                parentNavigationFragment.getNavController().navigate(R.id.action_navigation_category_list_to_navigation_category_update,bundle);
            }
        });
        binding.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.WARN, "DELETE", "DELETE pressed");
                View dialogView = layoutInflater.inflate(R.layout.dialog_delete_base, null);
                // Create the AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
                builder.setView(dialogView);
                // Set up the buttons
                Button cancelButton = dialogView.findViewById(R.id.btnCancel);
                Button deleteButton = dialogView.findViewById(R.id.btnDelete);
                TextView itemDescription = dialogView.findViewById(R.id.txtItemDescription);
                TextView itemTitle= dialogView.findViewById(R.id.txtProductMessageDisplay);
                itemDescription.setText(getCategory.CategoryName);
                itemTitle.setText("are you sure want to delete the category");
                // Create and show the dialog
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
                        dialogDeleteClick(getCategory);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }
    private void dialogDeleteClick(CategoryDto categoryDto) {
        // Perform the deletion logic here
        Toast.makeText(parentContext, "delete CATE with id + " + categoryDto.CategoryId, Toast.LENGTH_SHORT).show();
        Call deleteCategory = ApiClient.getServiceClient(CategoryServices.class).Delete(categoryDto.CategoryId);
        deleteCategory.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(parentContext,"DELETE CATE SUCCESS", Toast.LENGTH_SHORT).show();
                    categoryDtoList.remove(categoryDto);
                    notifyDataSetChanged();
                }
                else{
                    Log.e("DELETE CATE ERR", "fail to delte on server");
                    Toast.makeText(parentContext,"DELETE CATE ERROR from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("DELETE CATE ERR", throwable.getMessage(),throwable);
                Toast.makeText(parentContext,"DELETE CATE ERROR from client", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
