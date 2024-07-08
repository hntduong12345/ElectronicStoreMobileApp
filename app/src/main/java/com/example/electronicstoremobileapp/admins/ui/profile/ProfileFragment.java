package com.example.electronicstoremobileapp.admins.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.electronicstoremobileapp.Utility.UserLoggingUtil;
import com.example.electronicstoremobileapp.databinding.FragmentAdminProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentAdminProfileBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoggingUtil.LogOut(getContext());
                getActivity().finish();
            }
        });
        return view;
    }
}
