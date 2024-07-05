package com.example.electronicstoremobileapp.admins.ui.vouchers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.Helpers;
import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.admins.ui.vouchers.models.VoucherCreateDto;
import com.example.electronicstoremobileapp.admins.ui.vouchers.models.VoucherUpdateDto;
import com.example.electronicstoremobileapp.apiClient.ApiClient;
import com.example.electronicstoremobileapp.apiClient.vouchers.VoucherServices;
import com.example.electronicstoremobileapp.databinding.FragmentAdminVoucherDetailsBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherFragmentDetails  extends Fragment {

    FragmentAdminVoucherDetailsBinding binding;
    final Calendar myCalendar= Calendar.getInstance();
    Toolbar toolbar;
    VoucherDto voucherDto;
    final Calendar myDate= Calendar.getInstance();
    final Calendar myTime= Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";
    String hourFormat = "HH:mm";
    public VoucherFragmentDetails() {
        // Required empty public constructor
    }

    public static VoucherFragmentDetails newInstance() {
        VoucherFragmentDetails fragment = new VoucherFragmentDetails();
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
        binding = FragmentAdminVoucherDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getArguments() != null) {
            String id = getArguments().getString("VoucherId");
            GetVoucher(id);
        }
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
                    binding.txtExpiryTime.setText(sdf.format(myTime.getTime()));
                }
            }
        };
        toolbar = binding.toolbarDetail;
        toolbar.setTitle("Voucher Details");
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
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdate();
            }
        });
        return view;
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) {
            return navHostFragment.getNavController();
        }
        throw new NullPointerException("Nav controller is null in Voucher Detail Fragment");
    }
    private void bindToView() {
        binding.txtVoucherId.setText(Helpers.returnEmptyStringOrValue(voucherDto.VoucherId));
        binding.txtVoucherCode.setText(Helpers.returnEmptyStringOrValue(voucherDto.VoucherCode));
        binding.txtCreatedDate.setText(Helpers.returnEmptyStringOrValue(voucherDto.CreatedDate));
        String[] splitted = voucherDto.ExpiryDate.split(" ");
        binding.txtExpiryDate.setText(Helpers.returnEmptyStringOrValue(splitted[0]));
        binding.txtExpiryTime.setText(Helpers.returnEmptyStringOrValue(splitted[1]));
        binding.txtPercentage.setText(Helpers.returnEmptyStringOrValue(String.valueOf(voucherDto.Percentage)));
        binding.txtIsAvailable.setText(Helpers.returnEmptyStringOrValue(voucherDto.IsAvailable ? "Active" : "Deactive"));
    }

    void GetVoucher(String id){
        Call<VoucherDto> call = ApiClient.getServiceClient(VoucherServices.class).Get(id);
        call.enqueue(new Callback<VoucherDto>() {
            @Override
            public void onResponse(Call<VoucherDto> call, Response<VoucherDto> response) {
                int code = response.code();
                if (code < 200 || code > 300 || response.body() == null) {
                    Log.println(Log.ERROR, "API ERROR", "Error in fecth vouchers with status code " + code);
                    return;
                }
                VoucherDto responseBody = response.body();
                voucherDto = responseBody;
                bindToView();
            }

            @Override
            public void onFailure(Call<VoucherDto> call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in fecth voucher");
            }
        });
    }

    void onUpdate(){
        if(!CheckValidation()) return;
        int percent = Integer.valueOf(binding.txtPercentage.getText().toString());
        VoucherUpdateDto updated = new VoucherUpdateDto(
                binding.txtVoucherCode.getText().toString(),
                binding.txtExpiryDate.getText().toString() + " " + binding.txtExpiryTime.getText().toString(),
                percent,
                voucherDto.IsAvailable
        );
        Call call = ApiClient.getServiceClient(VoucherServices.class).Update(voucherDto.VoucherId, updated);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                int code = response.code();
                if (code < 200 || code > 300){
                    Log.println(Log.ERROR, "API ERROR", "Error in update voucher with status code " + code);
                    return;
                }
                Toast.makeText(getContext(), "Voucher updated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.println(Log.ERROR, "API ERROR", "Error in update voucher");
            }
        });
    }

    boolean CheckValidation(){
        if(StringUtils.isEmpty(binding.txtVoucherCode.getText().toString())){
            binding.txtVoucherCode.setError("Code can't be empty");
            return false;
        }
        if(StringUtils.isEmpty(binding.txtExpiryDate.getText().toString())){
            binding.txtExpiryDate.setError("Expiry Date can't be empty");
            return false;
        }
        if(StringUtils.isEmpty(binding.txtExpiryTime.getText().toString())){
            binding.txtExpiryTime.setError("Expiry Time can't be empty");
            return false;
        }
        if(StringUtils.isEmpty(binding.txtPercentage.getText().toString())){
            binding.txtPercentage.setError("Percentage can't be empty");
            return false;
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime expiryDate = LocalDateTime.parse(binding.txtExpiryDate.getText().toString() + " " + binding.txtExpiryTime.getText().toString(),dateFormatter);
        LocalDateTime createdDate = LocalDateTime.now();
        if(expiryDate.isBefore(createdDate)){
            binding.txtExpiryDate.setError("Expiry Date must be later than Created Date");
            return false;
        }
        Integer percent = Integer.valueOf(binding.txtPercentage.getText().toString());
        if(percent > 100 || percent <= 0){
            binding.txtPercentage.setError("Percentage must be lower than 100, greater than 0");
            return false;
        }
        return true;
    }
}
