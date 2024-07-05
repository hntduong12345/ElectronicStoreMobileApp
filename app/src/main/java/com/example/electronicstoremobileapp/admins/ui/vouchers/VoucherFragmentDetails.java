package com.example.electronicstoremobileapp.admins.ui.vouchers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
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
    DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    String myFormat="dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
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
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                binding.txtExpiryDate.setText(sdf.format(myCalendar.getTime()));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(myFormat);
        binding.txtVoucherId.setText(Helpers.returnEmptyStringOrValue(voucherDto.VoucherId));
        binding.txtVoucherCode.setText(Helpers.returnEmptyStringOrValue(voucherDto.VoucherCode));
        binding.txtCreatedDate.setText(Helpers.returnEmptyStringOrValue(voucherDto.CreatedDate));
        binding.txtExpiryDate.setText(Helpers.returnEmptyStringOrValue(voucherDto.ExpiryDate));
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
                binding.txtExpiryDate.getText().toString(),
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
