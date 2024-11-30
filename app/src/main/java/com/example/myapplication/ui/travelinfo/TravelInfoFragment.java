package com.example.myapplication.ui.travelinfo;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;
import android.widget.TextView;

public class TravelInfoFragment extends Fragment {

    private TravelInfoViewModel mViewModel;

    public static TravelInfoFragment newInstance() {
        return new TravelInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_travel_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startDateButton = view.findViewById(R.id.startDateButton);
        Button endDateButton = view.findViewById(R.id.endDateButton);
        TextView selectedStartDate = view.findViewById(R.id.selectedStartDate);
        TextView selectedEndDate = view.findViewById(R.id.selectedEndDate);

        startDateButton.setOnClickListener(v -> showDatePickerDialog(startDateButton, selectedStartDate));
        endDateButton.setOnClickListener(v -> showDatePickerDialog(endDateButton, selectedEndDate));
    }


    private void showDatePickerDialog(Button button, TextView selectedDateText) {
        Calendar calendar = Calendar.getInstance();

        // Provera da li je na dugmetu već postavljen datum
        String existingDate = button.getText().toString();
        if (!existingDate.contains("Izaberite")) {
            String[] parts = existingDate.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Mesec u Calendar-u počinje od 0
            int year = Integer.parseInt(parts[2]);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), null, year, month, day);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select Date", (dialog, which) -> {
            DatePicker picker = datePickerDialog.getDatePicker();
            int selectedYear = picker.getYear();
            int selectedMonth = picker.getMonth() + 1; // Month is zero-based
            int selectedDay = picker.getDayOfMonth();
            String formattedDate = selectedDay + "-" + selectedMonth + "-" + selectedYear;
            button.setText(formattedDate);
            selectedDateText.setText(formattedDate);
        });

        // Postavljanje boje dugmeta na crnu
        datePickerDialog.setOnShowListener(dialog -> {
            Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.payten_red));
        });

        datePickerDialog.show();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TravelInfoViewModel.class);
        // TODO: Use the ViewModel
    }


}