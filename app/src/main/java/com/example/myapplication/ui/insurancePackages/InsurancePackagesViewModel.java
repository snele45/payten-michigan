package com.example.myapplication.ui.insurancePackages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InsurancePackagesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public InsurancePackagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is insurance packets fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}