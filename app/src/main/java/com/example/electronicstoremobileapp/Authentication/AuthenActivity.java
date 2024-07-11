package com.example.electronicstoremobileapp.Authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.admins.MainActivity;
import com.example.electronicstoremobileapp.ui.customer_ui.HomePage.HomeActivity;

import kotlin.Triple;

public class AuthenActivity extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Triple<String, String, String> userInfo = UserLoggingUtil.GetUserInfo(this);
        if(userInfo.getThird() != null){
            Intent intent;
            if(userInfo.getThird().equals("CUSTOMER")){
                intent = new Intent(this, HomeActivity.class);
            }
            else{
                // TODO: CHANGE LATER
                intent = new Intent(this, MainActivity.class);
            }
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.authenFragment, LoginFragment.newInstance())
                .commit();
    }
}