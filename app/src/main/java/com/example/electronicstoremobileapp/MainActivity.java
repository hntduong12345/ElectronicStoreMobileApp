package com.example.electronicstoremobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    TextView home,product,order,profile;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.mainFragment, CustomerProfileFragment.newInstance(),null)
                .commit();
        home = findViewById(R.id.textViewHome);
        product = findViewById(R.id.textViewProduct);
        order = findViewById(R.id.textViewOrder);
        profile = findViewById(R.id.textViewProfile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = CustomerProfileFragment.newInstance();
                ReplaceFragment(frag);
            }
        });
    }

    void ReplaceFragment(Fragment fragment){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.mainFragment, fragment);
        transaction.commit();
    }
}