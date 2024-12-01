package com.example.myapplication.ui.qrcode;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.math.BigDecimal;

public class QrCodeFragment extends Fragment {

    private QrCodeViewModel mViewModel;
    private ImageView qrCodeImageView;

    private int price;

    public static QrCodeFragment newInstance() {
        return new QrCodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);
        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        if (getArguments() != null) {
            price = getArguments().getInt("price");
            // Use tmpValue as needed within your fragment
        }
        generateQrCode("https://www.youtube.com/watch?v=BHAJS4G75NI");

        Button payButton = view.findViewById(R.id.payNowButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal bd = new BigDecimal(price);
                ((MainActivity)getActivity()).performPayment((bd));
            }
        });

        return view;
    }

    private void generateQrCode(String text) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 200, 200);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QrCodeViewModel.class);
        // TODO: Use the ViewModel
    }

}