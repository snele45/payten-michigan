package com.example.myapplication.ui.esimPackets;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.math.BigDecimal;

public class ESimPacketsFragment extends Fragment {

    private ESimPacketsViewModel mViewModel;

    public static ESimPacketsFragment newInstance() {
        return new ESimPacketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_e_sim_packets, container, false);



        Button toEsimQrCodeButtonBasic = view.findViewById(R.id.esimBasicPackageButton);
        toEsimQrCodeButtonBasic.setOnClickListener(v -> {
            ((MainActivity)getActivity()).performPayment(new BigDecimal(990), 2, null);
        });

        Button toEsimQrCodeButtonStandard = view.findViewById(R.id.esimStandardPackageButton);
        toEsimQrCodeButtonStandard.setOnClickListener(v -> {
            ((MainActivity)getActivity()).performPayment(new BigDecimal(1990), 2, null);
        });

        Button toEsimQrCodeButtonPremium = view.findViewById(R.id.esimPremiumPackageButton);
        toEsimQrCodeButtonPremium.setOnClickListener(v -> {
            ((MainActivity)getActivity()).performPayment(new BigDecimal(2490), 2, null);
        });

        return view;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ESimPacketsViewModel.class);
        // TODO: Use the ViewModel
    }

}