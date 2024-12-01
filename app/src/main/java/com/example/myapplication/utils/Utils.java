package com.example.myapplication.utils;

import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApp;

public class Utils {
    public static int getColorFromAttribute(int attribute){
        TypedValue typedValue = new TypedValue();
        return ContextCompat.getColor(MyApp.appContext, typedValue.resourceId);
    }
}
