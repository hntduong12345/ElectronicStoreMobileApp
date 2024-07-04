package com.example.electronicstoremobileapp.admins.ui.vouchers.viewElements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.databinding.ListviewitemAdminVoucherListItemBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

public class VoucherAdapter extends BaseAdapter {
    Context parentContext;
    LayoutInflater layoutInflater;
    NavHostFragment parentNavigationFragment;
    List<VoucherDto> vouchers;
    public VoucherAdapter(Context parentContext, List<VoucherDto> vouchers, NavHostFragment parentNavigationFragment){
        this.parentContext = parentContext;
        this.vouchers = vouchers;
        this.layoutInflater = LayoutInflater.from(parentContext);
        this.parentNavigationFragment = parentNavigationFragment;
    }
    @Override
    public int getCount() {
        return vouchers.size();
    }

    @Override
    public VoucherDto getItem(int position) {
        return vouchers.get(position);
    }

    @Override
    public long getItemId(int position) {
        VoucherDto voucher = vouchers.get(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewitemAdminVoucherListItemBinding binding;
        if(convertView == null){
            binding = ListviewitemAdminVoucherListItemBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }
        else{
            binding = (ListviewitemAdminVoucherListItemBinding) convertView.getTag();
        }
        VoucherDto voucher = getItem(position);
        binding.txtVoucherCode.setText(voucher.VoucherCode);
        binding.txtVoucherAuthor.setText(voucher.Account.Email);
        binding.txtVoucherDuration.setText(voucher.CreatedDate + "-" + voucher.ExpiryDate);
        binding.txtVoucherStatus.setText(voucher.IsAvailable ? "Active" : "Deactive");
        return convertView;
    }
}
