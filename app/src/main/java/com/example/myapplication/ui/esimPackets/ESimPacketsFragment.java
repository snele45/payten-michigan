package com.example.myapplication.ui.esimPackets;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;

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
            NavController navController = Navigation.findNavController(v);Bundle bundle = new Bundle();
            bundle.putInt("price", 990); // Hardcoded for demonstration
            navController.navigate(R.id.action_nav_e_sim_packet_to_nav_qrcode, bundle);
        });

        Button toEsimQrCodeButtonStandard = view.findViewById(R.id.esimStandardPackageButton);
        toEsimQrCodeButtonStandard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);Bundle bundle = new Bundle();
            bundle.putInt("price", 1990); // Hardcoded for demonstration
            navController.navigate(R.id.action_nav_e_sim_packet_to_nav_qrcode, bundle);
        });

        Button toEsimQrCodeButtonPremium = view.findViewById(R.id.esimPremiumPackageButton);
        toEsimQrCodeButtonPremium.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);Bundle bundle = new Bundle();
            bundle.putInt("price", 2490 ); // Hardcoded for demonstration
            navController.navigate(R.id.action_nav_e_sim_packet_to_nav_qrcode, bundle);
        });

        return view;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ESimPacketsViewModel.class);
        // TODO: Use the ViewModel
    }

}