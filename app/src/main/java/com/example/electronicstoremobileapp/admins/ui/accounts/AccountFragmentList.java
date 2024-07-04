package com.example.electronicstoremobileapp.admins.ui.accounts;

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
import com.example.electronicstoremobileapp.admins.ui.accounts.viewElements.AccountListViewAdapter;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminAccountListBinding;
import com.example.electronicstoremobileapp.models.AccountDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragmentList extends Fragment {
    FragmentAdminAccountListBinding binding;
    Toolbar toolbar;
    List<AccountDto> accountLists;
    AccountListViewAdapter accountListViewAdapter;
    NavHostFragment parentFragment;

    // TODO: Rename and change types of parameters
    public AccountFragmentList() {
    }


    public static AccountFragmentList newInstance(String param1, String param2) {
        AccountFragmentList fragment = new AccountFragmentList();
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
        binding = FragmentAdminAccountListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.parentFragment = (NavHostFragment) getParentFragment();

        toolbar = binding.fragmentToolbar;
        toolbar.setTitle("Account");
        toolbar.inflateMenu(com.example.electronicstoremobileapp.R.menu.admin_account_fragment_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClicked(item);
            }
        });

        binding.btnGetAllAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllAdmin();
            }
        });
        binding.btnGetAllStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllStaff();
            }
        });
        binding.btnGetAllUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllCustomer();
            }
        });

        accountLists = new ArrayList<AccountDto>();
        //binding.btnGetAllStaff.callOnClick();
        fetchAllStaff();
        accountListViewAdapter = new AccountListViewAdapter(this.getContext(), accountLists, R.layout.listviewitem_admin_account_list_item, this.parentFragment);
        binding.fragmentAccountListView.setAdapter(accountListViewAdapter);

        return view;
        //return inflater.inflate(R.layout.fragment_account_list, container, false);
    }

    private boolean menuItemClicked(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_account_fragment_item_create) {
            Log.println(Log.WARN, "CLICK", "Create is clicked in ACCOUNT");
            NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
            if (parentFragment != null) {
                NavController navController = parentFragment.getNavController();
                navController.navigate(R.id.action_navigation_account_list_to_navigation_account_create);
                return true;
            }
        }
        return false;
    }

    private void fetchAllCustomer() {
        Call<List<AccountDto>> call = ApiClient.getServiceClient(AccountServices.class).GetAllCustomer();
        fecthData(call);
    }

    private void fetchAllAdmin() {
        Call<List<AccountDto>> call = ApiClient.getServiceClient(AccountServices.class).GetAllAdmin();
        fecthData(call);
    }

    private void fetchAllStaff() {
        Call<List<AccountDto>> call = ApiClient.getServiceClient(AccountServices.class).GetAllStaff();
        fecthData(call);
    }

    private void fecthData(Call<List<AccountDto>> call) {
        //Call<List<AccountDto>> call = ApiClient.getServiceClient(AccountServices.class);
        call.enqueue(new Callback<List<AccountDto>>() {
            @Override
            public void onResponse(Call<List<AccountDto>> call, Response<List<AccountDto>> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth account with status code " + code);
                    accountLists.add(new AccountDto("id", "first name", "Last name", "gmail@gmail.com", "pass", "address", "234324324", "STAFF", "NOT SURE"));
                    accountListViewAdapter.notifyDataSetChanged();
                    return;
                }
                List<AccountDto> responseBody = response.body();
                accountLists.clear();
                accountLists.addAll(responseBody);
                accountListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AccountDto>> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth products");
                return;
            }
        });
    }
}