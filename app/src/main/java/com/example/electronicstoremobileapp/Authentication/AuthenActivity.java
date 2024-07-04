package com.example.electronicstoremobileapp.Authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.electronicstoremobileapp.CustomerProfileFragment;
import com.example.electronicstoremobileapp.R;

public class AuthenActivity extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.authenFragment, LoginFragment.newInstance())
                .commit();

    }

}