package com.example.electronicstoremobileapp.admins.ui.products;

import static com.example.electronicstoremobileapp.AppConstant.TOBE_UPDATE_OBJECT_KEY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.products.models.UpdateProductDto;
import com.example.electronicstoremobileapp.admins.ui.products.utils.ProductHelpers;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProductDetailBinding;
import com.example.electronicstoremobileapp.models.CategoryDto;
import com.example.electronicstoremobileapp.models.ProductDto;

import org.apache.commons.lang3.StringUtils;


public class ProductDetailFragment extends Fragment {

    FragmentAdminProductDetailBinding binding;
    ProductDto selectedProduct;
    NavHostFragment navHostFragment;
    public ProductDetailFragment() {
        // Required empty public constructor
    }


    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
                Toast.makeText(this.getContext(), "CANNOT FOUND PRODUCT", Toast.LENGTH_LONG);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
            }
            selectedProduct = getProduct;
            NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
            navHostFragment = parentFragment;
            //            if (parentFragment != null) {
//                NavController navController = parentFragment.getNavController();
//                navController.navigate(R.id.action_navigation_product_update_to_navigation_product);
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProductDetailBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navHostFragment.getNavController().navigateUp();
            }
        });
        binding.btnToUpdateAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(TOBE_UPDATE_OBJECT_KEY,selectedProduct);
                navHostFragment.getNavController().navigate(R.id.action_navigation_product_detail_to_navigation_product_update_storage,bundle);
            }
        });
        binding.btnToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(TOBE_UPDATE_OBJECT_KEY,selectedProduct);
                navHostFragment.getNavController().navigate(R.id.action_navigation_product_detail_to_navigation_product_update,bundle);
            }
        });
        bindProductToField(selectedProduct);
        return view;
    }
    private void bindProductToField(ProductDto selectedProduct) {
        binding.textViewProductDetailName.setText(Helpers.returnEmptyStringOrValue(selectedProduct.ProductName));
        binding.textViewProductDetailPrice.setText(Helpers.returnEmptyStringOrValue(String.valueOf(selectedProduct.DefaultPrice)));

        binding.txtProductName.setText( Helpers.returnEmptyStringOrValue(selectedProduct.ProductName) );
        //binding.txt.setText( Helpers.returnEmptyStringOrValue(selectedProduct.Description) );
        binding.txtProductDefaultPrice.setText( Helpers.returnEmptyStringOrValue(String.valueOf(selectedProduct.DefaultPrice)) );
        binding.txtProductManufacturer.setText( Helpers.returnEmptyStringOrValue(selectedProduct.Manufacturer) );
        binding.txtStorageAmount.setText( Helpers.returnEmptyStringOrValue(String.valueOf(selectedProduct.StorageAmount)) );
        CategoryDto getSelectedCategory = selectedProduct.Category;

        binding.txtProductCategoryid.setText(getSelectedCategory.getCategoryId());
        binding.txtProductCategoryName.setText(getSelectedCategory.getCategoryName());

        binding.imageViewProductDetailImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ProductHelpers.setImageWithWebUrl(selectedProduct.RelativeUrl,binding.imageViewProductDetailImg);
        binding.btnToUpdate.setText("UPDATE");
        binding.btnToUpdateAmount.setText("UPDATE AMOUNT");

    }
}