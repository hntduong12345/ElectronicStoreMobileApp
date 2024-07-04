package com.example.electronicstoremobileapp.admins.vouchers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.adapter.VoucherAdapter;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

public class VoucherListFragment extends Fragment {
    ListView listView;
    VoucherAdapter voucherAdapter;

    public VoucherListFragment(){}
    public static VoucherListFragment Instance(){
        VoucherListFragment fragment = new VoucherListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_admin_voucher, container, false);

        return view;
    }

    void GetVouchers(){
        List<VoucherDto> vouchers;

    }
}
