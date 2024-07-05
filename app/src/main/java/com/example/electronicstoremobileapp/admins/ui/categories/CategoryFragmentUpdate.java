package com.example.electronicstoremobileapp.admins.ui.categories;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.categories.models.CreateCategoryDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminCategoryCreateBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminCategoryUpdateBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragmentUpdate extends Fragment {
    FragmentAdminCategoryUpdateBinding binding;
    Toolbar toolbar;
    public CategoryFragmentUpdate() {
        // Required empty public constructor
    }


    public static CategoryFragmentUpdate newInstance(String param1, String param2) {
        CategoryFragmentUpdate fragment = new CategoryFragmentUpdate();
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
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentAdminCategoryUpdateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        toolbar = binding.toolbarCreate;
        toolbar.setTitle("Update Category");
        toolbar.setNavigationIcon(com.example.electronicstoremobileapp.R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarNavClick(v);
            }
        });
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClick(v);
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_admin_category_update, container, false);
    }
    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            navController.navigate(R.id.action_navigation_category_update_to_navigation_category);
        }
    }
    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment == null) {
            throw new NullPointerException("no parent fragment found");
        }
        return navHostFragment.getNavController();
    }
    private boolean ValidateField() {
        boolean isValid = true;
        String categoryName = binding.txtCategoryName.getText().toString().trim();
        String categoryDescription = binding.txtCategoryDescription.getText().toString().trim();
        if (StringUtils.isBlank(categoryName) || categoryName.length() < 1 ) {
            binding.txtCategoryName.setError("category name cannot be empty");
            isValid = false;
        }

        if (StringUtils.isBlank(categoryDescription) || categoryDescription.length() < 1) {
            binding.txtCategoryDescription.setError("category description name cannot be empty");
            isValid = false;
        }
        return isValid;
    }

    private CreateCategoryDto bindToModel(String categoryName, String categoryDescription) {
        boolean isNullOrEmpty = StringUtils.isAnyEmpty(categoryName, categoryDescription);
        if (isNullOrEmpty) {
            return null;
        }
        return new CreateCategoryDto(categoryName,categoryDescription);
    }
    private void onUpdateClick(View view) {

        boolean validateResultSuccess = ValidateField();
        if (validateResultSuccess) {
            CreateCategoryDto createCategoryDto = bindToModel(binding.txtCategoryName.getText().toString(),
                    binding.txtCategoryDescription.getText().toString());
            if (createCategoryDto != null) {
                createCategory(createCategoryDto);
            }
            return;
        } else {
            Toast.makeText(CategoryFragmentUpdate.this.getContext(), "validation fail", Toast.LENGTH_SHORT).show();
        }
    }
    private void createCategory(CreateCategoryDto categoryToCreate) {
        Call<CategoryDto> createMethod = ApiClient.getServiceClient(CategoryServices.class).Create(categoryToCreate);
        createMethod.enqueue(new Callback<CategoryDto>() {
            @Override
            public void onResponse(Call<CategoryDto> call, Response<CategoryDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getParentFragment().getActivity(), "create success", Toast.LENGTH_SHORT);
                    Log.i("UPDATE SUCCESS", "UPDATE category success");
                    getNavController().navigate(R.id.action_navigation_category_create_to_navigation_category);
                } else {
                    Log.e("UPDATE ERROR", "cannot UPDATE, error bad request or other");
                    Toast.makeText(getContext(), "UPDATE fail, unknown error", Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onFailure(Call<CategoryDto> call, Throwable throwable) {
                Log.e("UPDATE ERROR", "cannot UPDATE, error 500 or internet", throwable);
                Toast.makeText(getContext(), "cannot UPDATE", Toast.LENGTH_SHORT);
            }
        });
    }
}