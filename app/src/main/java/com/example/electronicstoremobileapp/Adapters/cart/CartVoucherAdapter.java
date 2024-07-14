package com.example.electronicstoremobileapp.Adapters.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.electronicstoremobileapp.R;
import com.example.electronicstoremobileapp.databinding.ComponentVoucherItemRowBinding;
import com.example.electronicstoremobileapp.models.VoucherDto;

import java.util.List;

public class CartVoucherAdapter extends BaseAdapter {
    Context parentContext;
    List<VoucherDto> voucherItemLists;
    LayoutInflater layoutInflater;
    NavHostFragment hostFragment;

    public CartVoucherAdapter(Context parentContext, List<VoucherDto> voucherItemLists,NavHostFragment hostFragment) {
        this.parentContext = parentContext;
        this.voucherItemLists = voucherItemLists;
        this.layoutInflater = LayoutInflater.from(parentContext);
        this.hostFragment = hostFragment;
    }

    @Override
    public int getCount() {
        return voucherItemLists.size();
    }

    @Override
    public VoucherDto getItem(int position) {
        return voucherItemLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComponentVoucherItemRowBinding binding;

        if (convertView == null) {
            binding = ComponentVoucherItemRowBinding.inflate(layoutInflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ComponentVoucherItemRowBinding) convertView.getTag();
        }

        VoucherDto voucher = voucherItemLists.get(position);

        binding.textViewVoucherCode.setText(voucher.VoucherCode);
        String text = voucher.Percentage +"% OFF till " +voucher.ExpiryDate;
        binding.textViewVoucherPercent.setText(text);

        binding.buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavOptions option = new NavOptions.Builder()
                        .setEnterAnim(R.anim.enter_from_left)
                        .setExitAnim(R.anim.exit_to_right)
                        .build();
                Bundle bundle = new Bundle();
                bundle.putString("VoucherSelectedId",voucher.VoucherId);
                hostFragment.getNavController().navigate(R.id.action_cartVoucherFragment_to_cartPageFragment,bundle,option);
            }
        });

        //Optional click on quantity text to show out popup to insert quantity

        return convertView;
    }
}
