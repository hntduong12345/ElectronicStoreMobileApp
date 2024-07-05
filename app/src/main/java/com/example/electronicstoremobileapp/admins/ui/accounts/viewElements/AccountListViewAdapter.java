package com.example.electronicstoremobileapp.admins.ui.accounts.viewElements;

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
import com.example.electronicstoremobileapp.apiClient.products.ProductServices;
import com.example.electronicstoremobileapp.databinding.ListviewitemAdminAccountListItemBinding;
import com.example.electronicstoremobileapp.models.AccountDto;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountListViewAdapter extends BaseAdapter {

    Context parentContext;
    List<AccountDto> accountDtoList;
    int layoutId;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;

    public AccountListViewAdapter(Context parentContext, List<AccountDto> accountDtoList, int layoutId, NavHostFragment parentNavigationFragment) {
        this.parentContext = parentContext;
        this.accountDtoList = accountDtoList;
        this.layoutId = layoutId;
        this.layoutInflater = LayoutInflater.from(parentContext);
        this.parentNavigationFragment = parentNavigationFragment;
    }

    @Override
    public int getCount() {
        return accountDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountDtoList.get(position);
    }


    @Override
    public long getItemId(int position) {
        AccountDto getAccount = accountDtoList.get(position);
        String productId = getAccount.AccountId;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminAccountListItemBinding binding;
        if (convertView == null) {
            binding = ListviewitemAdminAccountListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ListviewitemAdminAccountListItemBinding) convertView.getTag();
        }
        AccountDto getAccount = (AccountDto) getItem(position);
        //binding.txtAccountId.setText(getAccount.AccountId);
        binding.txtFullname.setText(Helpers.returnEmptyStringOrValue(getAccount.FirstName) + " " + Helpers.returnEmptyStringOrValue(getAccount.LastName));
        //binding.txtAddress.setText(Helpers.returnEmptyStringOrValue(getAccount.Address));
        binding.txtEmail.setText(getAccount.Email);
        binding.txtRole.setText(getAccount.Role.toString());
        //binding.txtPhonenumber.setText(Helpers.returnEmptyStringOrValue(getAccount.PhoneNumber));

        //binding.imgProductImage.setImageURI(Uri.parse(getProduct.RelativeUrl));
        if (StringUtils.compareIgnoreCase(AppConstant.ADMIN_ROLE, getAccount.Role) != 0) {
            binding.btnDeleteProduct.setEnabled(false);
            binding.btnDeleteProduct.setImageDrawable(null);
        }else{
            binding.btnDeleteProduct.setEnabled(true);
            binding.btnDeleteProduct.setImageDrawable(parentContext.getDrawable(R.drawable.baseline_delete_24));
        }
        binding.btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.WARN, "UPDATE", "UPDATE pressed");
            }
        });
        binding.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.WARN, "DELETE", "DELETE pressed");
                View dialogView = layoutInflater.inflate(com.example.electronicstoremobileapp.R.layout.dialog_admin_account_delete, null);
                // Create the AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
                builder.setView(dialogView);
                // Set up the buttons
                Button cancelButton = dialogView.findViewById(R.id.btnCancel);
                Button deleteButton = dialogView.findViewById(R.id.btnDelete);
                TextView accountId = dialogView.findViewById(R.id.txtAccountId);
                accountId.setText(getAccount.Email);
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
                        // Handle the delete action
                        dialogDeleteClick(getAccount);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        binding.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentNavigationFragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("AccountDetail", getAccount);
                    parentNavigationFragment.getNavController().navigate(R.id.action_navigation_account_list_to_navigation_account_detail, bundle);
                }
            }
        });
        return convertView;
    }

    private void dialogDeleteClick(AccountDto accountDto) {
        // Perform the deletion logic here
        Toast.makeText(parentContext, "delete account with id + " + accountDto.AccountId, Toast.LENGTH_SHORT).show();
        Call deleteAccount = ApiClient.getServiceClient(AccountServices.class).Delete(accountDto.AccountId);
        deleteAccount.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(parentContext,"DELETE ACC SUCCESS", Toast.LENGTH_SHORT).show();
                    accountDtoList.remove(accountDto);
                    notifyDataSetChanged();
                }
                else{
                    Log.e("DELETE ACC ERR", "fail to delte on server");
                    Toast.makeText(parentContext,"DELETE ACC ERROR from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("DELETE ACC ERR", throwable.getMessage(),throwable);
                Toast.makeText(parentContext,"DELETE ACC ERROR from client", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
