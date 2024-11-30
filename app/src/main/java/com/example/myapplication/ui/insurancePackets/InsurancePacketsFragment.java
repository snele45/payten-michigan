package com.example.myapplication.ui.insurancePackets;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.Locale;

public class InsurancePacketsFragment extends Fragment {

    private InsurancePacketsViewModel mViewModel;
    private TextView priceTextView;
    private int totalPrice = 0;
    public static InsurancePacketsFragment newInstance() {
        return new InsurancePacketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_packets, container, false);

        // Initialize views
        priceTextView = view.findViewById(R.id.priceTextView);
        CheckBox checkbox1 = view.findViewById(R.id.checkbox1);
        CheckBox checkbox2 = view.findViewById(R.id.checkbox2);

        // Set listeners for checkboxes
        checkbox1.setOnCheckedChangeListener(new PriceChangeListener(10));
        checkbox2.setOnCheckedChangeListener(new PriceChangeListener(20));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsurancePacketsViewModel.class);
        // TODO: Use the ViewModel
    }

    private String formatPrice(double price) {
        // Use Serbian locale to apply correct formatting
        Locale serbianLocale = new Locale("sr", "RS");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(serbianLocale);

        // Format price to two decimal places
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(price) + " RSD";
    }

    private class PriceChangeListener implements CompoundButton.OnCheckedChangeListener {
        private final int price;

        public PriceChangeListener(int price) {
            this.price = price;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                totalPrice += price;
            } else {
                totalPrice -= price;
            }
            priceTextView.setText(formatPrice(totalPrice));
        }
    }

}