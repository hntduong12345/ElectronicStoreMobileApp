package com.example.electronicstoremobileapp.admins.ui.vouchers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.AppConstant;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.admins.ui.accounts.models.RegisterAccountModel;
import com.example.electronicstoremobileapp.admins.ui.vouchers.models.VoucherCreateDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.accounts.AccountServices;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminAccountCreateBinding;
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherCreateBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherCreateFragment extends Fragment {

    FragmentAdminVoucherCreateBinding binding;
    final Calendar myDate= Calendar.getInstance();
    final Calendar myTime= Calendar.getInstance();
    Toolbar toolbar;
    VoucherDto voucherDto;
    DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    String dateFormat = "dd/MM/yyyy";
    String hourFormat = "HH:mm";

    public VoucherCreateFragment() {
        // Required empty public constructor
    }

    public static VoucherCreateFragment newInstance() {
        VoucherCreateFragment fragment = new VoucherCreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminVoucherCreateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH,month);
                myDate.set(Calendar.DAY_OF_MONTH,day);
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                binding.txtExpiryDate.setText(sdf.format(myDate.getTime()));
            }
        };
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat sdf = new SimpleDateFormat(hourFormat);
                    binding.txtExpiryTime.setText(sdf.format(myDate.getTime()));
                }
            }
        };
        toolbar = binding.toolbarDetail;
        toolbar.setTitle("Voucher Create");
        toolbar.setNavigationIcon(com.example.electronicstoremobileapp.R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    navController.navigate(R.id.action_navigation_voucher_details_to_navigation_voucher);
                }
            }
        });
        binding.txtExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myDate.get(Calendar.YEAR),myDate.get(Calendar.MONTH),myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.txtExpiryTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(),time,myTime.get(Calendar.HOUR),myDate.get(Calendar.MINUTE), true).show();
            }
        });
        binding.txtIsAvailable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getContext(), binding.txtIsAvailable);
                menu.getMenuInflater().inflate(R.menu.admin_voucher_edit_is_available, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.VoucherStatusActive){
                            binding.txtIsAvailable.setText("Active");
                            voucherDto.IsAvailable = true;
                        }
                        if(item.getItemId() == R.id.VoucherStatusDeactive){
                            binding.txtIsAvailable.setText("Deactive");
                            voucherDto.IsAvailable = false;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate();
            }
        });
        return view;
    }

    private void toolbarNavClick(View view) {
        NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
        if (parentFragment != null) {
            NavController navController = parentFragment.getNavController();
            // navigateUp() works just find, we just testing here
            //navController.navigateUp();
            navController.navigate(R.id.action_navigation_voucher_create_to_navigation_voucher);
        }
    }

    void onCreate(){
        if(!CheckValidation()) return;
        int percent = Integer.valueOf(binding.txtPercentage.getText().toString());
        VoucherCreateDto created = new VoucherCreateDto(
                binding.txtVoucherCode.getText().toString(),
                binding.txtExpiryDate.getText().toString() + " " + binding.txtExpiryTime.getText().toString(),
                percent,
                voucherDto.IsAvailable
        );
        String id = UserLoggingUtil.GetUserInfo(getContext()).getFirst();
        Call call = ApiClient.getServiceClient(VoucherServices.class).Create(id, created);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                int code = response.code();
                if (code < 200 || code > 300){
                    Log.println(Log.ERROR, "API ERROR", "Error in create voucher with status code " + code);
                    return;
                }
                Toast.makeText(getContext(), "Voucher created", Toast.LENGTH_LONG).show();
                NavHostFragment parentFragment = (NavHostFragment) getParentFragment();
                if (parentFragment != null) {
                    NavController navController = parentFragment.getNavController();
                    // navigateUp() works just find, we just testing here
                    //navController.navigateUp();
                    navController.navigate(R.id.action_navigation_voucher_create_to_navigation_voucher);
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in create voucher");
            }
        });
    }

    boolean CheckValidation(){
        LocalDateTime expiryDate = LocalDateTime.parse(voucherDto.ExpiryDate);
        LocalDateTime createdDate = LocalDateTime.parse(voucherDto.CreatedDate);
        if(expiryDate.isBefore(createdDate))
            Toast.makeText(this.getContext(), "Expiry Date must be later than Created Date", Toast.LENGTH_SHORT).show();
        if(voucherDto.Percentage > 100)
            Toast.makeText(this.getContext(), "Percentage must be lower than 100", Toast.LENGTH_SHORT).show();
        else
            return true;
        return false;
    }
}
