package com.example.electronicstoremobileapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.admins.MainActivity;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AuthService;
import com.example.electronicstoremobileapp.Authentication.models.Login;
import com.example.electronicstoremobileapp.Authentication.models.LoginResponse;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText userInput,userPassword;
    Button login;
    TextView errorText,signup;

    AuthService authService;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    void login(){
        String input = userInput.getText().toString();
        String password = userPassword.getText().toString();
        if(TextUtils.isEmpty(input) || TextUtils.isEmpty(password)){
            errorText.setText("Missing field");
            return;
        }
        Login login = new Login(input,password);
        try{
            Call<LoginResponse> call = authService.Login(login);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            String token = response.body().accessToken;
                            String role = UserLoggingUtil.LogIn(getActivity(), token);
                            Intent intent;
                            if (role.equals("CUSTOMER")){
                                intent = new Intent(getActivity(), HomeActivity.class);
                            }
                            else {
                                // TODO: CHANGE LATER
                                intent = new Intent(getActivity(), MainActivity.class);
                            }
                            startActivity(intent);
                        }
                    }
                    else{
                        String error = response.message();
                        errorText.setText(error);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }


    void signup(){
        Fragment register = RegisterFragment.newInstance();
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.authenFragment, register);
        transaction.commit();

        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        userInput = view.findViewById(R.id.editTextEmailSI);
        userPassword = view.findViewById(R.id.editTextPasswordSI);
        errorText = view.findViewById(R.id.textViewError);
        login = view.findViewById(R.id.buttonSignIn);
        //loginWithGoogle = findViewById(R.id.loginGoogle);
        signup = view.findViewById(R.id.textViewSignUp);
        authService = ApiClient.getClient().create(AuthService.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        return view;
    }
}
