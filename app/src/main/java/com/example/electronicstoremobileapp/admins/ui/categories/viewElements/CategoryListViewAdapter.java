package com.example.electronicstoremobileapp.admins.ui.categories.viewElements;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminAccountListItemBinding;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminCategoryListItemBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.CategoryDto;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
                itemDescription.setText(getCategory.CategoryName);
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
                        //dialogDeleteClick(getAccount);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }
}
