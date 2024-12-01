package com.example.myapplication.ui.insurancePackages;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.Button;
import com.example.myapplication.R;


import java.text.NumberFormat;
import java.util.Locale;

public class InsurancePackagesFragment extends Fragment {

    private InsurancePackagesViewModel mViewModel;
    private TextView priceTextViewBasic;
    private TextView priceTextViewStandard;
    private TextView priceTextViewPremium;

    private int totalPriceBasic = 1890;
    private int totalPriceStandard = 2390;
    private int totalPricePremium = 2990;

    public static InsurancePackagesFragment newInstance() {
        return new InsurancePackagesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_packages, container, false);


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
        basic_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerBasic(200));
        basic_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerBasic(300));
        basic_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerBasic(200));
        basic_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerBasic(900));

        standard_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerStandard(200));
        standard_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerStandard(300));
        standard_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerStandard(200));
        standard_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerStandard(900));

        premium_checkbox1.setOnCheckedChangeListener(new PriceChangeListenerPremium(200));
        premium_checkbox2.setOnCheckedChangeListener(new PriceChangeListenerPremium(300));
        premium_checkbox3.setOnCheckedChangeListener(new PriceChangeListenerPremium(200));
        premium_checkbox4.setOnCheckedChangeListener(new PriceChangeListenerPremium(900));

        Button basicPackageButton = view.findViewById(R.id.basic_package);  // Button to navigate
        basicPackageButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_insurancePackages_to_nav_passport);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsurancePackagesViewModel.class);
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