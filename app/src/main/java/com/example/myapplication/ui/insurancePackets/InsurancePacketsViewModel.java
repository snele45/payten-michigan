package com.example.myapplication.ui.insurancePackets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InsurancePacketsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InsurancePacketsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is insurance packets fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}