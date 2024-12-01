package com.example.myapplication.ui.passport;

import android.app.AlertDialog;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;

public class PassportFragment extends Fragment {

    private NfcAdapter nfcAdapter;
    private Handler handler = new Handler();

    public static PassportFragment newInstance() {
        return new PassportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passport, container, false);
        initializeNfcAdapter();
        return view;
    }

    private void initializeNfcAdapter() {
        Context context = getActivity();
        if (context != null) {
            nfcAdapter = NfcAdapter.getDefaultAdapter(context);
            if (nfcAdapter == null) {
              //  Toast.makeText(context, "NFC is not available on this device.", Toast.LENGTH_LONG).show();
            } else if (!nfcAdapter.isEnabled()) {
                Toast.makeText(context, "NFC is disabled.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(this::simulateNfcTagRead, 5000); // Delay of 10 seconds
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(this::simulateNfcTagRead); // Remove the callback to prevent memory leaks
    }

    private void showNfcReadSuccessDialog() {
        if (getActivity() != null) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("NFC Scan")
                    .setMessage("Podaci uspešno očitani.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    private void simulateNfcTagRead() {
        showNfcReadSuccessDialog();
    }
}
