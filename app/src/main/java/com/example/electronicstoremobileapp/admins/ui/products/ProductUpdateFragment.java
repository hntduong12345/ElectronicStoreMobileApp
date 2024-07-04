package com.example.electronicstoremobileapp.admins.ui.products;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.models.CreateProductDto;
import com.example.electronicstoremobileapp.admins.ui.products.models.UpdateProductDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductUpdateBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductUpdateFragment extends Fragment {
    FragmentAdminProductUpdateBinding binding;
    UpdateProductDto updateProductDto;
    ProductDto selectedProduct;
    UpdateProductDto modelToUpdate;
    String idProductToUpdate;
    Spinner dropdownCategory;
    List<CategoryDto> categoryDtoList;
    Toolbar updateToolbar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProductUpdateFragment() {
        // Required empty public constructor
        updateProductDto = new UpdateProductDto();
        categoryDtoList = new ArrayList<>();
        LocalDateTime now = Helpers.getLocalDateTime();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ProductUpdateFragment newInstance(String param1, String param2) {
        ProductUpdateFragment fragment = new ProductUpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ProductDto getProduct = (ProductDto) getArguments().get(AppConstant.TOBE_UPDATE_OBJECT_KEY);
            if (getProduct == null) {
                Toast.makeText(this.getContext(), "CANNOT FOUND PRODUCT TO UPDATE", Toast.LENGTH_LONG);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
            }
            selectedProduct = getProduct;
            modelToUpdate = new UpdateProductDto();
            modelToUpdate.CategoryId = selectedProduct.CategoryId;
            modelToUpdate.ProductName = selectedProduct.ProductName;
            modelToUpdate.DefaultPrice = selectedProduct.DefaultPrice;
            modelToUpdate.Description = selectedProduct.Description;
            modelToUpdate.Manufacturer = selectedProduct.Manufacturer;
            modelToUpdate.StorageAmount = selectedProduct.StorageAmount;
            idProductToUpdate = selectedProduct.ProductId;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProductUpdateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dropdownCategory = binding.dropdownCategories;
        updateToolbar = binding.toolbarCreate;
        updateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarNavClick(v);
            }
        });
        binding.btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClick();
            }
        });
        binding.btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProductDto.ImageFile = null;
                binding.imgSelectedImage.setTag(R.string.IMAGE_VIEW_TAG_URI,null);
                setImageWithWebUrl(selectedProduct.RelativeUrl,binding.imgSelectedImage);
            }
        });
        binding.btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        fetchData();
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == AppConstant.SELECT_PICTURE_CODE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.imgSelectedImage.setImageURI(selectedImageUri);
                    binding.imgSelectedImage.setTag(R.string.IMAGE_VIEW_TAG_URI, selectedImageUri.toString());
                }
            }
        }
    }
    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
        }
    }
    private void addNewErrorToErrorList(String field, String message){
        ScrollView scrollView = binding.scrollViewError;
        LinearLayout scollviewErrorLayout = (LinearLayout) scrollView.getChildAt(0);
        TextView txtError = new TextView(getContext());
        txtError.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        txtError.setBackgroundColor(Color.WHITE);
        txtError.setTextColor(Color.RED);
        txtError.setText("" + field + " || " + message);
        scollviewErrorLayout.addView(txtError);
    }
    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AppConstant.SELECT_PICTURE_CODE);
    }
    private void bindToUpdateModel() {
        updateProductDto.CategoryId = ((CategoryDto) dropdownCategory.getSelectedItem()).CategoryId;
        updateProductDto.StorageAmount = Integer.valueOf(binding.edtStorageAmount.getText().toString());
        updateProductDto.DefaultPrice = Double.valueOf(binding.edtDefaultPrice.getText().toString());
        updateProductDto.ProductName = String.valueOf(binding.edtProductName.getText().toString());
        //this is for image only
        String imageUri = (String) binding.imgSelectedImage.getTag(R.string.IMAGE_VIEW_TAG_URI);
        if (imageUri != null) {
            String pathToImage = getImagePathFromUri(Uri.parse(imageUri));
            if (pathToImage != null) {
                updateProductDto.ImageFile = getFileFromPath(pathToImage);
            } else {
                updateProductDto.ImageFile = null;
            }
        }
        updateProductDto.Description = String.valueOf(binding.edtDescription.getText().toString());
        updateProductDto.Manufacturer = String.valueOf(binding.edtManufacturer.getText().toString());
    }

    private String getImagePathFromUri(Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getActivity().getContentResolver().query(imageUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    private File getFileFromPath(String filePath) {
        File file = new File(filePath);
        return file;
    }
    private void fetchData() {
        Call<List<CategoryDto>> getCategories = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        getCategories.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                if (! response.isSuccessful()) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                    return;
                }
                categoryDtoList.clear();
                categoryDtoList.addAll(response.body());
                ArrayAdapter<CategoryDto> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryDtoList);
                adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                dropdownCategory.setAdapter(adapter);
                //ArrayAdapter<CategoryDto> adapter = (ArrayAdapter<CategoryDto>) dropdownCategory.getAdapter();
                adapter.notifyDataSetChanged();
                dropdownCategory.setSelection(0);

                bindProductToField(selectedProduct);
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
            }
        });
    }

    private void bindProductToField(ProductDto selectedProduct) {
        binding.edtProductName.setText( Helpers.returnEmptyStringOrValue(selectedProduct.ProductName) );
        binding.edtDescription.setText( Helpers.returnEmptyStringOrValue(selectedProduct.Description) );
        binding.edtDefaultPrice.setText( Helpers.returnEmptyStringOrValue(String.valueOf(selectedProduct.DefaultPrice)) );
        binding.edtManufacturer.setText( Helpers.returnEmptyStringOrValue(selectedProduct.Manufacturer) );
        binding.edtStorageAmount.setText( Helpers.returnEmptyStringOrValue(String.valueOf(selectedProduct.StorageAmount)) );
        CategoryDto getSelectedCategory = categoryDtoList
                .stream()
                .filter(categoryDto ->  StringUtils.equals(categoryDto.CategoryId, selectedProduct.CategoryId) )
                .findFirst()
                .get();
        int index = categoryDtoList.indexOf(getSelectedCategory);
        dropdownCategory.setSelection(index);

        binding.imgSelectedImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setImageWithWebUrl(selectedProduct.RelativeUrl,binding.imgSelectedImage);
    }
    private void setImageWithWebUrl(String url, ImageView imageView){
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(imageView);
    }
    private boolean validateField(){
        UpdateProductDto model = updateProductDto;
        boolean isValid = true;
        String productName = model.ProductName;
        String description = model.Description;
        String categoryId = model.CategoryId;
        String manufacturer= model.Manufacturer;
        int storageAmount = model.StorageAmount;
        double defaultPrice = model.DefaultPrice;
        File imageFile = model.ImageFile;
        if (StringUtils.isBlank(productName) || productName.length() <= 1 ) {
            binding.edtProductName.setError("product name must not null and length > 1 ");
            isValid = false;
        }

        if (StringUtils.isBlank(description) || description.isEmpty()) {
            binding.edtDescription.setError("description must not empty");
            isValid = false;
        }
        if (StringUtils.isBlank(categoryId)) {
            addNewErrorToErrorList("category id", "category is null");
            isValid = false;
        }
        if (StringUtils.isBlank(manufacturer)) {
            binding.edtManufacturer.setError("manufacturer must not empty");
            isValid = false;
        }
        if (storageAmount < 0 || storageAmount > 100000) {
            binding.edtStorageAmount.setError("storageAmount must be at least 0 and < 10000");
            isValid = false;
        }
        if (defaultPrice < 0 || defaultPrice > 900000000.0) {
            binding.edtDefaultPrice.setError("defaultPrice > 0 and less then 900 million");
            isValid = false;
        }
//        if (imageFile == null){
//            addNewErrorToErrorList("image file", "image file is empty, please add an image");
//        }
        return isValid;
    }
    private void onUpdateClick() {
        bindToUpdateModel();
        boolean validateResultSuccess = validateField();
        if(validateResultSuccess == false ){
            Toast.makeText(this.getContext(),"validation error",Toast.LENGTH_SHORT).show();
            return;
        }
        MultipartBody.Part imageMultipart = null;;
        if(updateProductDto.ImageFile != null){
            File imageFile = updateProductDto.ImageFile;
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), imageFile);
            imageMultipart = MultipartBody.Part.createFormData("ImageFile", imageFile.getName(), requestFile);
        }

        RequestBody description = RequestBody.create(MultipartBody.FORM, updateProductDto.Description);
        RequestBody productName = RequestBody.create(MultipartBody.FORM, updateProductDto.ProductName);
        RequestBody defaultPrice = RequestBody.create(MultipartBody.FORM, String.valueOf(updateProductDto.DefaultPrice));
        RequestBody manufacturer = RequestBody.create(MultipartBody.FORM, updateProductDto.Manufacturer);
        RequestBody storageAmount = RequestBody.create(MultipartBody.FORM, String.valueOf(updateProductDto.StorageAmount));
        RequestBody categoryId = RequestBody.create(MultipartBody.FORM, updateProductDto.CategoryId);

        Call updateProducrt = ApiClient.getServiceClient(ProductServices.class)
                .Update(selectedProduct.ProductId,
                        productName,
                        description,
                        defaultPrice,
                        categoryId,
                        manufacturer,
                        storageAmount,
                        imageMultipart);
        updateProducrt.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Log.w("UPDATE SUCCESS", "UPDATE success");
//                    binding.btnBack.callOnClick();
                    toolbarNavClick(new View(getContext()));
                } else {
                    try {
                        Gson gson = new GsonBuilder().create();
                        String errorBody = response.errorBody().string();
                        JsonParser.parseString(errorBody);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("UPDATE ERROR", "Fail to create, read the detail");
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("UPDATE ERROR", throwable.getMessage());
            }
        });
    }
}