package com.example.electronicstoremobileapp.admins.ui.accounts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminAccountDetailsBinding;
import com.example.electronicstoremobileapp.models.AccountDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragmentDetails extends Fragment {

    FragmentAdminAccountDetailsBinding binding;

    Toolbar toolbar;
    AccountDto accountDto;

    public AccountFragmentDetails() {
        // Required empty public constructor
    }

    public static AccountFragmentDetails newInstance(AccountDto accountDto) {
        AccountFragmentDetails fragment = new AccountFragmentDetails();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            AccountDto accountDto = (AccountDto) getArguments().get("AccountDetail");
            this.accountDto = accountDto;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAccountDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        toolbar = binding.toolbarDetail;
        toolbar.setTitle("Account Details");
        toolbar.setNavigationIcon(com.example.electronicstoremobileapp.R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_navigation_account_detail_to_navigation_account);
                }
            }
        });
        binding.btnBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBanClick(v);
            }
        });
        bindToView();
        return view;
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) {
            return navHostFragment.getNavController();
        }
        throw new NullPointerException("Nav controller is null in Account Detail Fragment");
    }

    private void bindToView() {
        binding.txtAccountId.setText(Helpers.returnEmptyStringOrValue(accountDto.AccountId));
        binding.txtFirstname.setText(Helpers.returnEmptyStringOrValue(accountDto.FirstName));
        binding.txtLastname.setText(Helpers.returnEmptyStringOrValue(accountDto.LastName));
        binding.txtAddress.setText(Helpers.returnEmptyStringOrValue(accountDto.Address));
        binding.txtEmail.setText(Helpers.returnEmptyStringOrValue(accountDto.Email));
        binding.txtRole.setText(Helpers.returnEmptyStringOrValue(accountDto.Role));
        binding.txtStatus.setText(Helpers.returnEmptyStringOrValue(accountDto.Status));
    }

    private void onBanClick(View view) {
        String accountId = accountDto.AccountId;
        if (accountId == null) {
            return;
        }
        Call banCall = ApiClient.getServiceClient(AccountServices.class).Ban(accountId);
        banCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Change Status Success", Toast.LENGTH_SHORT);
                    getNavController().navigate(R.id.action_navigation_account_detail_to_navigation_account);
                    return;
                } else {
                    Toast.makeText(getContext(), "Change Status Fail", Toast.LENGTH_SHORT);
                    return;
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("UPDATE ERROR", "update account status error", throwable);
            }
        });
    }
}