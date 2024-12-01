package com.example.myapplication.ui.travelinfo;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;
import java.util.Calendar;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

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

        Button nextStepButton = view.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).performPayment();

            }
        });
       // nextStepButton.setOnClickListener(v -> initiatePayment());
    }


    /*private void initiatePayment() {
        Intent paymentIntent = new Intent("com.payten.ecr.action");
        paymentIntent.setPackage("com.payten.paytenapos");

        // Definisanje JSON-a za plaćanje
        String jsonPaymentData = "{"
                + "\"amount\": 100,"
                + "\"currency\": \"RSD\","
                + "\"transactionType\": \"sale\""
                + "}";

        // Dodavanje JSON podataka kao extra u Intent
        paymentIntent.putExtra("ecrJson", jsonPaymentData);
        paymentIntent.putExtra("senderIntentFilter", "com.example.myapplication.RESPONSE");
        paymentIntent.putExtra("senderPackage", getActivity().getPackageName());
        paymentIntent.putExtra("senderClass", this.getClass().getName());

        // Opciono: Ako aplikacija mora da reaguje na zaustavljene pakete
        paymentIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        try {
            startActivity(paymentIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("Payment", "Payment application is not installed.", e);
        }
    }
*/
    private void showDatePickerDialog(Button button, TextView selectedDateText) {
        Calendar calendar = Calendar.getInstance();

        // Provera da li je na dugmetu već postavljen datum
        String existingDate = button.getText().toString();
        if (!existingDate.isEmpty() && !existingDate.equals("SELECT START DATE") && !existingDate.equals("SELECT END DATE")) {
            String[] parts = existingDate.split("-");
            if (parts.length == 3) {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1; // Mesec u Calendar-u počinje od 0
                int year = Integer.parseInt(parts[2]);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (getContext() != null) {
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
                positiveButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
            });

            Log.d("DatePickerDialog", "Showing dialog with date: " + day + "-" + (month+1) + "-" + year);
            datePickerDialog.show();
        } else {
            Log.e("DatePickerDialog", "Context is null, cannot show DatePickerDialog");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TravelInfoViewModel.class);
        // TODO: Use the ViewModel
    }
}
