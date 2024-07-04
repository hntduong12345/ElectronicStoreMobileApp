package com.example.electronicstoremobileapp.ui.customer_ui.HomePage;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CartPageFragment;
import com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI.ShopPageFragment;
import com.example.electronicstoremobileapp.ui.customer_ui.UserPage.UserPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView, bottomProductNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

//        bottomProductNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewProductNav);
//        bottomProductNavigationView.setVisibility(View.GONE);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewHomeNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomePageFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                return true;
            case R.id.shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ShopPageFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                return true;
            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new CartPageFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                return true;
            case R.id.person:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new UserPageFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                return true;
        }
        return false;
    }


}
