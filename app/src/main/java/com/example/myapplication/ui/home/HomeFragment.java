package com.example.myapplication.ui.home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.Button;
import com.example.myapplication.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button selectedButton = null; // Track the previously selected button
    private Button nextBtn = null; // Track the previously selected button
    private int btnType; // Variable for navigation action
    List<Button> buttons;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nextBtn = root.findViewById(R.id.nextStepButton);
        Button insBtn = root.findViewById(R.id.insuranceButton);
        Button esimBtn = root.findViewById(R.id.esimButton);
        Button bundleBtn = root.findViewById(R.id.bundleButton);

        buttons = Arrays.asList(insBtn, esimBtn, bundleBtn);

        // Assign the listener to each button
        insBtn.setOnClickListener(v -> {
            buttonColors((Button) v, R.drawable.home_button_red);
            btnType = R.id.action_nav_home_to_nav_travelInfo;
        });
        esimBtn.setOnClickListener(v -> {
            buttonColors((Button) v, R.drawable.home_button_red);
            btnType = R.id.action_nav_home_to_esim_travel_info;
        });
        bundleBtn.setOnClickListener(v -> {
            buttonColors((Button) v, R.drawable.home_button_red);
            btnType = R.id.action_nav_home_to_nav_travelInfo;
        });
        nextBtn.setOnClickListener(this::changeView);

        // Find the button and set an OnClickListener
//        Button insuranceButton = root.findViewById(R.id.insuranceButton);
//        insuranceButton.setOnClickListener(v -> {
//            // Use Navigation Component to navigate to the other fragment
//            NavController navController = Navigation.findNavController(v);
//            navController.navigate(R.id.action_nav_home_to_nav_travelInfo);
//        });
//
//        Button esimButton = root.findViewById(R.id.esimButton);
//        esimButton.setOnClickListener(v -> {
//            // Use Navigation Component to navigate to the other fragment
//            NavController navController = Navigation.findNavController(v);
//            navController.navigate(R.id.action_nav_home_to_esim_travel_info);
//        });
        return root;
    }

    private void buttonColors(Button clickedButton, int style){
        // Reset the previously selected button
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }
        clickedButton.setSelected(true);
        selectedButton = clickedButton;
        selectedButton.setBackground(getResources().getDrawable(R.drawable.home_button_red));
        selectedButton.setTextColor(Color.parseColor("#FFFFFF"));
        int colorInt = getResources().getColor(R.color.white);
        ColorStateList csl = ColorStateList.valueOf(colorInt);
        ((com.google.android.material.button.MaterialButton)selectedButton).setIconTint(csl);

        for (Button button: buttons) {
            if(button == clickedButton){
                continue;
            }

            button.setBackground(getResources().getDrawable(R.drawable.home_button));
            button.setTextColor(Color.parseColor("#000000"));
            int blackColorInt = getResources().getColor(R.color.crnaboja);
            ColorStateList cslBlack = ColorStateList.valueOf(blackColorInt);
            ((com.google.android.material.button.MaterialButton)button).setIconTint(cslBlack);
        }

        // Update the next button color
        nextBtn.setBackground(getResources().getDrawable(R.drawable.home_button_red_small));
        nextBtn.setTextColor(Color.parseColor("#FFFFFF"));
    }
    private void changeView(View v){
        if (btnType != -1) {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(btnType);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}