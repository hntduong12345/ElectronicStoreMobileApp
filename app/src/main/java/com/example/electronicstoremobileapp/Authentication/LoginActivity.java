package com.example.electronicstoremobileapp.Authentication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.electronicstoremobileapp.MainActivity;
import com.example.electronicstoremobileapp.Model.Authentication.Interface.AuthService;
import com.example.electronicstoremobileapp.Model.Authentication.Login;
import com.example.electronicstoremobileapp.Model.Authentication.LoginResponse;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Repository.AuthRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText userInput;
    EditText userPassword;
    Button login,loginWithGoogle,signup;
    TextView errorText;

    AuthService authService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userInput = findViewById(R.id.userInput);
        userPassword = findViewById(R.id.userPass);
        errorText = findViewById(R.id.ErrorText);
        login = findViewById(R.id.login);
        loginWithGoogle = findViewById(R.id.loginGoogle);
        signup = findViewById(R.id.signup);

        authService = AuthRepository.getAuthService();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginGoogle();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
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
                            errorText.setText(token);
                        }
                    }
                    else{
                        if(response.code() == 400)
                            errorText.setText("Can't login now");
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                errorText.setText(jObjError.getString("Error"));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    void loginGoogle(){

    }

    void signup(){

    }
}
