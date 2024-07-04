package com.example.electronicstoremobileapp.admins.ui.accounts;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.accounts.models.RegisterAccountModel;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminAccountCreateBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragmentCreate extends Fragment {

    FragmentAdminAccountCreateBinding binding;
    Toolbar toolbar;
    Spinner dropdownRoleSelect;
    List<String> roleSelectionList;

    public AccountFragmentCreate() {
        // Required empty public constructor
    }

    public static AccountFragmentCreate newInstance(String param1, String param2) {
        AccountFragmentCreate fragment = new AccountFragmentCreate();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        roleSelectionList = new ArrayList<>();
        roleSelectionList.add(AppConstant.ADMIN_ROLE);
        roleSelectionList.add(AppConstant.STAFF_ROLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAccountCreateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, roleSelectionList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dropdownRoleSelect = binding.dropdownRoleSelect;
        dropdownRoleSelect.setAdapter(adapter);
        dropdownRoleSelect.setSelection(0);
        dropdownRoleSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar = binding.toolbarCreate;
        toolbar.setTitle("Account Create");
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
                onCreateClick(v);
            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_account_create, container, false);
    }

    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            // navigateUp() works just find, we just testing here
            //navController.navigateUp();
            navController.navigate(R.id.action_navigation_account_create_to_navigation_account);
        }
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment == null) {
            throw new NullPointerException("no parent fragment found");
        }
        return navHostFragment.getNavController();
    }

    private void onCreateClick(View view) {
        boolean validateResultSuccess = ValidateField();
        if (validateResultSuccess) {
            RegisterAccountModel accountCreate = bindToModel(binding.editTextEmailAddress.getText().toString().trim(),
                    binding.editTextPassword.getText().toString().trim(),
                    binding.editTextFirstname.getText().toString().trim(),
                    binding.editTextLastname.getText().toString().trim());
            if (accountCreate != null) {
                createAccount(accountCreate);
            }
            return;
        } else {
            Toast.makeText(getContext(), "validation fail", Toast.LENGTH_SHORT);
        }
    }

    private boolean ValidateField() {
        boolean isValid = true;
        String email = binding.editTextEmailAddress.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();
        String confirmPassword = binding.editTextConfirmPassword.getText().toString().trim();
        String firstname = binding.editTextFirstname.getText().toString().trim();
        String lastname = binding.editTextLastname.getText().toString().trim();
        if (StringUtils.isBlank(email) || email.length() < 5 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmailAddress.setError("Email must be at least 5 characters and have to match format email");
            isValid = false;
        }

        if (StringUtils.isBlank(password) || password.length() < 3) {
            binding.editTextPassword.setError("Password must be at least 3 characters");
            isValid = false;
        }
        if (StringUtils.isBlank(confirmPassword) || StringUtils.compare(confirmPassword, password) != 0) {
            binding.editTextConfirmPassword.setError("confirm Password not match");
            isValid = false;
        }
        if (StringUtils.isBlank(firstname) | firstname.length() < 5) {
            binding.editTextFirstname.setError("Firstname must be at least 5 characters");
            isValid = false;
        }

        if (StringUtils.isBlank(lastname) || lastname.length() < 5) {
            binding.editTextLastname.setError("Lastname must be at least 5 characters");
            isValid = false;
        }
        return isValid;
    }

    private RegisterAccountModel bindToModel(String email, String password, String Firstname, String Lastname) {
        boolean isNullOrEmpty = StringUtils.isAnyEmpty(email, password, Firstname, Lastname);
        if (isNullOrEmpty) {
            return null;
        }
        return new RegisterAccountModel(email, password, Firstname, Lastname);
    }

    private void createAccount(RegisterAccountModel accountToCreate) {
        Call<Void> createMethod;
        String getSelectedRole = (String) dropdownRoleSelect.getSelectedItem();
        if (getSelectedRole.equals(AppConstant.ADMIN_ROLE)) {
            createMethod = ApiClient.getServiceClient(AccountServices.class).CreateAdmin(accountToCreate);
        } else if (getSelectedRole.equals(AppConstant.STAFF_ROLE)) {
            createMethod = ApiClient.getServiceClient(AccountServices.class).CreateStaff(accountToCreate);
        } else {
            Toast.makeText(getParentFragment().getContext(), "cannot find role to create", Toast.LENGTH_SHORT);
            return;
        }
        createMethod.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getParentFragment().getActivity(), "create success", Toast.LENGTH_SHORT);
                    Log.i("CREATE SUCCESS", "create account success");
                    getNavController().navigate(R.id.action_navigation_account_create_to_navigation_account);
                } else {
                    Log.e("CREATE ERROR", "cannot create, error bad request or other");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("CREATE ERROR", "cannot create, error 500 or internet", throwable);
                Toast.makeText(getContext(), "cannot create", Toast.LENGTH_SHORT);
            }
        });
    }
}