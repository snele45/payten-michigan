package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "Intent received (including ECR response): " + intent.getAction());

        String responseResult = intent.getStringExtra("ResponseResult");

        if (MyApp.currentActivity instanceof MainActivity) {
            Log.d("RECEIVER", "MainActivity is already running, passing response.");
            MainActivity mainActivity = (MainActivity) MyApp.currentActivity;
            if (responseResult != null && !responseResult.isEmpty()) {
                mainActivity.handleECRResponse(responseResult); // Pass the response to the running MainActivity
            }
        } else {
            Log.d("RECEIVER", "MainActivity not running, starting it to handle the response.");
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("ResponseResult", responseResult);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
    }
}
