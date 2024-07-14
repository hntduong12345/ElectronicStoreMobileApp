package com.example.electronicstoremobileapp.ui.customer_ui.UserPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentHomePageBinding;
import com.example.electronicstoremobileapp.databinding.FragmentUserPageBinding;
import com.example.electronicstoremobileapp.models.AccountDto;
import com.example.electronicstoremobileapp.models.VoucherDto;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomePageFragment;

import kotlin.Triple;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPageFragment extends Fragment {

    private final String testUserId = ""; //Add user id for pre-test
    AccountDto profile;
    FragmentUserPageBinding binding;
    public NavController navController;
/*

    public UserPageFragment() {
        // Required empty public constructor
    }


    public static UserPageFragment newInstance() {
        UserPageFragment fragment = new UserPageFragment();
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
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Triple<String, String, String> userInfo = UserLoggingUtil.GetUserInfo(getContext());
        if(userInfo.getFirst() != null){
            GetProfile(userInfo.getFirst());
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserLoggingUtil.LogOut(getContext());
                    getActivity().finish();
                }
            });
        }
        return view;
    }

    void GetProfile(String id){
        Call<AccountDto> call = ApiClient.getServiceClient(AccountServices.class).GetProfile(id);
        call.enqueue(new Callback<AccountDto>() {
            @Override
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in profile with status code " + code);
                    return;
                }
                AccountDto responseBody = response.body();
                profile = responseBody;
                binding.textViewProfileFullname.setText(profile.FirstName+" "+profile.LastName);
                binding.textViewProfileEmail.setText(profile.Email);
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth profile");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.btnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_userPageFragment_to_userOrdersList);
                }
            }
        });
    }

    private void navigateToFragment(int fragmentId) {
        navController.navigate(fragmentId);
    }
}