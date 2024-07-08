package com.example.electronicstoremobileapp.admins.ui.products;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.models.CreateProductDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.categories.CategoryServices;
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductCreateBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.ProductDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductCreateFragment extends Fragment {

    FragmentAdminProductCreateBinding binding;
    Toolbar toolbar;
    Spinner dropdownCategory;
    List<CategoryDto> categoryDtoList;
    CreateProductDto newProductModel;

    public ProductCreateFragment() {
        newProductModel = new CreateProductDto();
    }

    // TODO: Rename and change types and number of parameters
    public static ProductCreateFragment newInstance() {
        ProductCreateFragment fragment = new ProductCreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        categoryDtoList = new ArrayList<CategoryDto>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProductCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.edtDefaultPrice.setText(String.valueOf(newProductModel.getDefaultPrice()));
        binding.edtStorageAmount.setText(String.valueOf(newProductModel.getStorageAmount()));

        toolbar = binding.toolbarCreate;
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Create Product");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarNavClick(v);
            }
        });
        dropdownCategory = binding.dropdownCategories;
        ArrayAdapter<CategoryDto> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, categoryDtoList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dropdownCategory.setAdapter(adapter);
        dropdownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropdownCategory.setSelection(position);
                CategoryDto getCate = categoryDtoList.get(position);
                Log.println(Log.WARN, "Category Selected", "select category with id " + getCate.CategoryId + " and have name " + getCate.CategoryName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        fetchData();

        binding.btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scollviewErrorLayout.removeAllViews();
                onCreateClick();
//                bindToNewModel();
//                Validator<CreateProductDto> validator = new CreateProductDto.CreateProductDtoValidator();
//                ValidationResult result = validator.validate(newProductModel);
//                if (false) {
//                    //if (result.isValid() == false) {
//                    ScrollView scrollView = binding.scrollViewError;
//                    LinearLayout scollviewErrorLayout = (LinearLayout) scrollView.getChildAt(0);
//                    scollviewErrorLayout.removeAllViews();
//                    for (Error err : result.getErrors()) {
//                        TextView txtError = new TextView(getContext());
//                        txtError.setLayoutParams(new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                        ));
//                        txtError.setBackgroundColor(Color.WHITE);
//                        txtError.setTextColor(Color.RED);
//                        txtError.setText("Field: " + err.getField() + " | message: " + err.getMessage());
//                        scollviewErrorLayout.addView(txtError);
//                    }
//                } else {
//                    binding.scollviewErrorLayout.removeAllViews();
//                    onCreateClick();
//                }
            }
        });
        return root;
        //return inflater.inflate(R.layout.fragment_product_create, container, false);
    }
    // this function is triggered when user
    // selects the image from the imageChooser
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
    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            navController.navigate(R.id.action_navigation_product_create_to_navigation_product);
        }
    }
    private void fetchData() {
        Call<List<CategoryDto>> getCategories = ApiClient.getServiceClient(CategoryServices.class).GetAll();
        getCategories.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                    return;
                }
                categoryDtoList.clear();
                categoryDtoList.addAll(response.body());
                ArrayAdapter<CategoryDto> adapter = (ArrayAdapter<CategoryDto>) dropdownCategory.getAdapter();
                adapter.notifyDataSetChanged();
                dropdownCategory.setSelection(0);

                CategoryDto getSelectedItem = (CategoryDto) dropdownCategory.getSelectedItem();
                newProductModel.CategoryId = getSelectedItem.CategoryId;
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth categories");
                return;
            }
        });
    }
    private void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        //startActivityForResult(Intent.createChooser(i, "Select Picture"), AppConstant.SELECT_PICTURE_CODE);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AppConstant.SELECT_PICTURE_CODE);
    }
    private void bindToNewModel() {
        newProductModel.CategoryId = ((CategoryDto) dropdownCategory.getSelectedItem()).CategoryId;
        newProductModel.StorageAmount = Integer.valueOf(binding.edtStorageAmount.getText().toString());
        newProductModel.DefaultPrice = Double.valueOf(binding.edtDefaultPrice.getText().toString());
        newProductModel.ProductName = String.valueOf(binding.edtProductName.getText().toString());
        //this is for image only
        String imageUri = (String) binding.imgSelectedImage.getTag(R.string.IMAGE_VIEW_TAG_URI);
        if (imageUri != null) {
            String pathToImage = getImagePathFromUri(Uri.parse(imageUri));
            if (pathToImage != null) {
                newProductModel.ImageFile = getFileFromPath(pathToImage);
            } else {
                newProductModel.ImageFile = null;
            }
        }

        //this is for image only

        newProductModel.Description = String.valueOf(binding.edtDescription.getText().toString());
        newProductModel.Manufacturer = String.valueOf(binding.edtManufacturer.getText().toString());
    }

    private String getImagePathFromUri(Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
//        CursorLoader loader =
//                new CursorLoader(this.getContext().getApplicationContext(),
//                        imageUri,
//                        projection,
//                        null,
//                        null,
//                        null);
//        Cursor cursor = loader.loadInBackground();
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
    private boolean validateField(){
        CreateProductDto model = newProductModel;
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
        if (imageFile == null){
            addNewErrorToErrorList("image file", "image file is empty, please add an image");
        }
        return isValid;
    }
    private void onCreateClick() {
        bindToNewModel();
        boolean validateResultSuccess = validateField();
        if(validateResultSuccess == false ){
            Toast.makeText(this.getContext(),"validation error",Toast.LENGTH_SHORT).show();
            return;
        }
        File imageFile = newProductModel.ImageFile;
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), imageFile);
        MultipartBody.Part imageMultipart = MultipartBody.Part.createFormData("ImageFile", imageFile.getName(), requestFile);

        RequestBody description = RequestBody.create(MultipartBody.FORM, newProductModel.Description);
        RequestBody productName = RequestBody.create(MultipartBody.FORM, newProductModel.ProductName);
        RequestBody defaultPrice = RequestBody.create(MultipartBody.FORM, String.valueOf(newProductModel.DefaultPrice));
        RequestBody manufacturer = RequestBody.create(MultipartBody.FORM, newProductModel.Manufacturer);
        RequestBody storageAmount = RequestBody.create(MultipartBody.FORM, String.valueOf(newProductModel.StorageAmount));
        RequestBody categoryId = RequestBody.create(MultipartBody.FORM, newProductModel.CategoryId);

        Call<ProductDto> createProduct = ApiClient.getServiceClient(ProductServices.class)
                .Create(productName,
                        description,
                        defaultPrice,
                        categoryId,
                        manufacturer,
                        storageAmount,
                        imageMultipart);
        createProduct.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                if (response.isSuccessful()) {
                    Log.w("CREATE SUCCESS", "Create success");
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
                    Log.e("CREATE ERROR", "Fail to create, read the detail");
                    //var errorModel = new Gson().fromJson(errorBody,class);
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable throwable) {
                Log.e("CREATE ERROR", throwable.getMessage());
            }
        });

    }
}