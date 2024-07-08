package com.example.electronicstoremobileapp.Adapters.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.models.CategoryDto;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CategoryDto> categoryList;

    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;

    public CategoryAdapter(Context context, int layout, List<CategoryDto> categoryList, NavHostFragment parentNavigationFragment) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
        this.parentNavigationFragment = parentNavigationFragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        TextView tv_CategoryName = convertView.findViewById(R.id.textViewCategoryRowName);
        CategoryDto category = categoryList.get(position);
        tv_CategoryName.setText(category.getCategoryName());

        return convertView;
    }
}
