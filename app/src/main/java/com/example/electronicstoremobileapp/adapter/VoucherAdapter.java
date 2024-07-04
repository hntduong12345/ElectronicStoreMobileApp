package com.example.electronicstoremobileapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

public class VoucherAdapter extends BaseAdapter {
    Activity activity;
    List<VoucherDto> vouchers;
    public VoucherAdapter(Activity activity, List<VoucherDto> vouchers){
        this.activity = activity;
        this.vouchers = vouchers;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.admin_voucher_item,null);
        return null;
    }
}
