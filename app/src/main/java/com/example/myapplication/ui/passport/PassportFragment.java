package com.example.myapplication.ui.passport;

import android.app.AlertDialog;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.math.BigDecimal;

public class PassportFragment extends Fragment {

    private NfcAdapter nfcAdapter;
    private int price;
    private Handler handler = new Handler();

    public static PassportFragment newInstance() {
        return new PassportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passport, container, false);
        if (getArguments() != null) {
            price = getArguments().getInt("price");
            // Use tmpValue as needed within your fragment
        }

        initializeNfcAdapter();
        return view;
    }

    private void initializeNfcAdapter() {
        Context context = getActivity();
        if (context != null) {
            nfcAdapter = NfcAdapter.getDefaultAdapter(context);
            if (nfcAdapter == null) {
           //     Toast.makeText(context, "NFC is not available on this device.", Toast.LENGTH_LONG).show();
            } else if (!nfcAdapter.isEnabled()) {
                Toast.makeText(context, "NFC is disabled.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(this::simulateNfcTagRead, 5000); // Delay of 5 seconds
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(this::simulateNfcTagRead); // Remove the callback to prevent memory leaks
    }

    private void showNfcReadSuccessDialog() {
        if (getActivity() != null) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_nfc_scan, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);

            // References for inputs and icon
            ImageView tickIcon = dialogView.findViewById(R.id.tickIcon);
            EditText inputFullName = dialogView.findViewById(R.id.inputFullName);
            EditText inputCitizenNumber = dialogView.findViewById(R.id.inputCitizenNumber);
            EditText inputAddress = dialogView.findViewById(R.id.inputAddress);
            Button buttonContinue = dialogView.findViewById(R.id.buttonContinue);

            // Set default values for inputs
            inputFullName.setText("Stefan Neskovic");
            inputCitizenNumber.setText("1110000790013");
            inputAddress.setText("Podgradina 33, Kosjeric");

            // Disable editing for inputs
            inputFullName.setEnabled(false);
            inputCitizenNumber.setEnabled(false);
            inputAddress.setEnabled(false);

            AlertDialog dialog = builder.create();

            buttonContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    BigDecimal bd = new BigDecimal(price);
                    ((MainActivity)getActivity()).performPayment( bd );
                }
                //    NavController navController = Navigation.findNavController(v);
                //  navController.navigate(R.id.action_nav_insurancePackages_to_nav_travelInfo);
            });

            dialog.show();
        }
    }

    private void simulateNfcTagRead() {
        showNfcReadSuccessDialog();
    }
}
