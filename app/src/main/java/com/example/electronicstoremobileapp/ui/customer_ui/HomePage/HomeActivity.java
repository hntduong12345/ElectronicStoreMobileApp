package com.example.electronicstoremobileapp.ui.customer_ui.HomePage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.JwtUtil;
import com.example.electronicstoremobileapp.admins.MainActivity;
import com.example.electronicstoremobileapp.databinding.ActivityHomeBinding;
import com.example.electronicstoremobileapp.ui.customer_ui.Cart_Order.CartPageFragment;
import com.example.electronicstoremobileapp.ui.customer_ui.ShopPage.ProductUI.ShopPageFragment;
import com.example.electronicstoremobileapp.ui.customer_ui.UserPage.UserPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getColor(R.color.primary_500));
        BottomNavigationView navViewCustomer = (BottomNavigationView) findViewById(R.id.nav_view_customer);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_home, R.id.nav_shop, R.id.nav_cart, R.id.nav_person)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_customer);
        NavigationUI.setupWithNavController(binding.navViewCustomer, navController);

        //Check permission
        if (allPermissionsGranted() == false) {
            //requestPermissions();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstant.STORAGE_PERMISSIONs_CODE) {
            if (permissions.length == grantResults.length) {
                boolean isAllGranted = true;
                for (int grantedOrNot : grantResults) {
                    if (grantedOrNot == PackageManager.PERMISSION_GRANTED) {
                        continue;
                    }
                    isAllGranted = false;
                }
                if (isAllGranted == false) {
                    //openAppSettings();
                    //finish();
                    Toast.makeText(HomeActivity.this, "PERMISSION IS NOT GRANTED FOR STORAGE", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(HomeActivity.this, "ALL PERMISSION IS GRANTED FOR STORAGE", Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
        }
    }

    private void checkPermissions() {
        String[] getPermissionRequired = AppConstant.STORAGE_PERMISSIONs;
        List<String> permissionToAskFor = new ArrayList<String>();
        for (String permission : getPermissionRequired) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionToAskFor.add(permission);
            }
        }
        if (permissionToAskFor.size() > 0) {
            requestPermissions(permissionToAskFor.toArray(new String[permissionToAskFor.size()]), AppConstant.STORAGE_PERMISSIONs_CODE);
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean allPermissionsGranted() {
        for (String permission : AppConstant.STORAGE_PERMISSIONs) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        if (true) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This app needs storage access to function properly.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HomeActivity.this, AppConstant.STORAGE_PERMISSIONs, AppConstant.STORAGE_PERMISSIONs_CODE);
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, AppConstant.STORAGE_PERMISSIONs, AppConstant.STORAGE_PERMISSIONs_CODE);
        }
    }
}
