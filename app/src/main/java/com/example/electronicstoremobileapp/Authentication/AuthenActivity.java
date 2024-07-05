package com.example.electronicstoremobileapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.auth0.android.jwt.JWT;
import com.example.electronicstoremobileapp.MainActivity;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.JwtUtil;
import com.example.electronicstoremobileapp.Utility.Preference;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;

import kotlin.Triple;

public class AuthenActivity extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Triple<String, String, String> userInfo = UserLoggingUtil.GetUserInfo(this);
        if(userInfo.getThird() != null){
            Intent intent;
            if(userInfo.getThird().equals("CUSTOMER")){
                intent = new Intent(this, MainActivity.class);
            }
            else{
                // TODO: CHANGE LATER
                intent = new Intent(this, com.example.electronicstoremobileapp.admins.MainActivity.class);
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