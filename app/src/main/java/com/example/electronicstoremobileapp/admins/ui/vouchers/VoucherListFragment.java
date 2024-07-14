package com.example.electronicstoremobileapp.admins.ui.vouchers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.vouchers.viewElements.VoucherAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherListBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherListFragment extends Fragment {
    FragmentAdminVoucherListBinding binding;
    List<VoucherDto> vouchers;
    Toolbar toolbar;
    VoucherAdapter voucherAdapter;
    NavHostFragment parentFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentAdminVoucherListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vouchers = new ArrayList<VoucherDto>();
        parentFragment = (NavHostFragment) getParentFragment();
        toolbar = binding.fragmentToolbar;
        toolbar.inflateMenu(R.menu.admin_voucher_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });
        GetVouchers();
        voucherAdapter = new VoucherAdapter(this.getContext(), vouchers, parentFragment);
        binding.ListViewVoucher.setAdapter(voucherAdapter);
        return root;
    }

    void GetVouchers(){
        Call<List<VoucherDto>> call = ApiClient.getServiceClient(VoucherServices.class).GetAll();
        call.enqueue(new Callback<List<VoucherDto>>() {
            @Override
            public void onResponse(Call<List<VoucherDto>> call, Response<List<VoucherDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth products with status code " + code);
                    return;
                }
                List<VoucherDto> responseBody = response.body();
                vouchers.clear();
                vouchers.addAll(responseBody);
                voucherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VoucherDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth voucher");
            }
        });
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
    private boolean menuItemClicked(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.toolbarCreate){
            NavHostFragment parentFragment = (NavHostFragment)  getParentFragment();
            if(parentFragment != null){
                NavController navController = parentFragment.getNavController();
                navController.navigate(R.id.action_navigation_voucher_list_to_navigation_voucher_create);
            }
            return true;
        }
        return false;
    }
}
