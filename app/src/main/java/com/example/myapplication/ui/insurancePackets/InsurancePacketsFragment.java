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
    private TextView priceTextViewBasic;
    private TextView priceTextViewStandard;
    private TextView priceTextViewPremium;

    private int totalPriceBasic = 1890;
    private int totalPriceStandard = 2390;
    private int totalPricePremium = 2990;

    public static InsurancePacketsFragment newInstance() {
        return new InsurancePacketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_packets, container, false);

        // Initialize views
        priceTextViewBasic = view.findViewById(R.id.priceTextViewBasic);
        priceTextViewStandard = view.findViewById(R.id.priceTextViewStandard);
        priceTextViewPremium = view.findViewById(R.id.priceTextViewPremium);

        CheckBox basic_checkbox1 = view.findViewById(R.id.basic_checkbox1);
        CheckBox basic_checkbox2 = view.findViewById(R.id.basic_checkbox2);
        CheckBox basic_checkbox3 = view.findViewById(R.id.basic_checkbox3);
        CheckBox basic_checkbox4 = view.findViewById(R.id.basic_checkbox4);

        CheckBox standard_checkbox1 = view.findViewById(R.id.standard_checkbox1);
        CheckBox standard_checkbox2 = view.findViewById(R.id.standard_checkbox2);
        CheckBox standard_checkbox3 = view.findViewById(R.id.standard_checkbox3);
        CheckBox standard_checkbox4 = view.findViewById(R.id.standard_checkbox4);

        CheckBox premium_checkbox1 = view.findViewById(R.id.premium_checkbox1);
        CheckBox premium_checkbox2 = view.findViewById(R.id.premium_checkbox2);
        CheckBox premium_checkbox3 = view.findViewById(R.id.premium_checkbox3);
        CheckBox premium_checkbox4 = view.findViewById(R.id.premium_checkbox4);

        // Set listeners for checkboxes
        basic_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerBasic(10));
        basic_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerBasic(20));
        basic_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerBasic(20));
        basic_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerBasic(20));

        standard_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerStandard(10));
        standard_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerStandard(20));
        standard_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerStandard(20));
        standard_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerStandard(20));

        premium_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerPremium(10));
        premium_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerPremium(20));
        premium_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerPremium(20));
        premium_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerPremium(20));

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

    private class PriceChangeListenerBasic implements CompoundButton.OnCheckedChangeListener {
        private final int price;

        public PriceChangeListenerBasic(int price) {
            this.price = price;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                totalPriceBasic += price;
            } else {
                totalPriceBasic -= price;
            }
            priceTextViewBasic.setText(formatPrice(totalPriceBasic));
        }
    }

    private class PriceChangeListenerStandard implements CompoundButton.OnCheckedChangeListener {
        private final int price;

        public PriceChangeListenerStandard(int price) {
            this.price = price;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                totalPriceStandard += price;
            } else {
                totalPriceStandard -= price;
            }
            priceTextViewStandard.setText(formatPrice(totalPriceStandard));
        }
    }

    private class PriceChangeListenerPremium implements CompoundButton.OnCheckedChangeListener {
        private final int price;

        public PriceChangeListenerPremium(int price) {
            this.price = price;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                totalPricePremium += price;
            } else {
                totalPricePremium -= price;
            }
            priceTextViewPremium.setText(formatPrice(totalPricePremium));
        }
    }

}