package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.ecr.EcrJsonReq;
import com.example.myapplication.ecr.EcrJsonRsp;
import com.example.myapplication.ecr.EcrRequestTransactionType;
import com.example.myapplication.ecr.EcrResponseCode;
import com.example.myapplication.transaction.TransactionData;
import com.example.myapplication.transaction.TransactionUtils;
import com.example.myapplication.utils.HashUtils;
import com.example.myapplication.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.payten.service.PaytenAidlInterface;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private ConstraintLayout mainScreen;
    private ConstraintLayout resultScreen;
    private ConstraintLayout systemScreen;

    TextView resultText;
    ImageButton btnPrint;
    Button btnOk;
    ImageButton btnSystemClose;
    ImageButton btnSystemOk;
    ProgressBar progressBar;
    CountDownTimer resultScreenTimer;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private PaytenAidlInterface paytenAidlInterface;
    private final Executor executor = Executors. newSingleThreadExecutor();
    public static final int ECR_NONE = 0;
    public static final int ECR_TRANSACTION = 1;
    public static final int ECR_SETTLE = 2;
    public static final int ECR_VOID = 3;
    public static int ecrAction = ECR_NONE;

    EcrJsonRsp resp = null;
    private boolean serviceOrPayment = true;
    private boolean android10Spec = false;
    private BigDecimal billTotal = new BigDecimal("145.23");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_insurancePackets)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        Button nextStepButton = findViewById(R.id.nextStepButton);

        //    nextStepButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    void showResultScreen(boolean inProgress){
        mainScreen.setVisibility(View.GONE);
        systemScreen.setVisibility(View.GONE);
        resultScreen.setVisibility(View.VISIBLE);

        if (inProgress){
            resultText.setText("Transaction in progress...");
            progressBar.setVisibility(View.VISIBLE);
            btnPrint.setVisibility(View.INVISIBLE);
            btnOk.setVisibility(View.INVISIBLE);
        }
        else{
            new Handler(Looper.getMainLooper()).post(() -> resultScreenTimer = new CountDownTimer(10 * 1000, 10 * 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            returnToMainScreen();
                        }
                    }.start()
            );

            progressBar.setVisibility(View.GONE);
            btnOk.setVisibility(View.VISIBLE);

            if (ecrAction == ECR_TRANSACTION) {
                if (resp != null && resp.response != null && resp.response.financial != null && resp.response.financial.result != null &&
                        resp.response.financial.result.code != null && resp.response.financial.result.code.equals(EcrResponseCode.approved) &&
                        resp.response.financial.id != null && resp.response.financial.id.card != null) {
                    // Update last transaction
                    int id = MyApp.transactionList.size() - 1;
                    MyApp.transactionList.get(id).invoice = resp.response.financial.id.invoice;
                    MyApp.transactionList.get(id).authorization = resp.response.financial.id.authorization;
                    MyApp.transactionList.get(id).reference = resp.response.financial.id.reference;
                    MyApp.transactionList.get(id).cardName = resp.response.financial.id.card.name;
                    MyApp.transactionList.get(id).pan = resp.response.financial.id.card.pan;
                    MyApp.transactionList.get(id).bin = resp.response.financial.id.card.bin;
                    if (resp.response.financial.amounts != null) {
                        MyApp.transactionList.get(id).base = resp.response.financial.amounts.base;
                    }
                    MyApp.transactionList.get(id).pending = false;
                    MyApp.transactionList.get(id).dateTime = resp.response.financial.dateTime;

                    btnPrint.setVisibility(View.VISIBLE);

                    // Display transaction success
                    resultText.setText("Transaction successful.");
                    resultText.setTextColor(getColor(R.color.payten_red));
                } else {
                    // Delete last attempted transaction from the list
                    MyApp.transactionList.remove(MyApp.transactionList.size() - 1);

                    // Display transaction failed
                    resultText.setText("Transaction failed!");
                    resultText.setTextColor(getColor(R.color.payten_red));
                }

                TransactionUtils.storeTransactions(MyApp.transactionList);
            }
            else if (ecrAction == ECR_SETTLE){
                if (resp != null && resp.response != null && resp.response.financial != null && resp.response.financial.result != null &&
                        resp.response.financial.result.code != null && resp.response.financial.result.code.equals(EcrResponseCode.approved)) {
                    MyApp.transactionList = new ArrayList<>();
                    TransactionUtils.storeTransactions(MyApp.transactionList);

                    // Display settlement success
                    resultText.setText("Settlement successful.");
                    resultText.setTextColor(getColor(R.color.white));
                }
                else{
                    // Display settlement failed
                    resultText.setText("Settlement failed!");
                    resultText.setTextColor(getColor(R.color.payten_red));
                }
            }
            else if (ecrAction == ECR_VOID){
                if (resp != null && resp.response != null && resp.response.financial != null && resp.response.financial.result != null &&
                        resp.response.financial.result.code != null && resp.response.financial.result.code.equals(EcrResponseCode.approved)) {
                    //  MyApp.transactionList.get(voidTranIndex).voided = true;
                    TransactionUtils.storeTransactions(MyApp.transactionList);

                    // Display settlement success
                    resultText.setText("Void successful.");
                    //   resultText.setTextColor(getColor(R.color.green));
                }
                else{
                    // Display void failed
                    resultText.setText("Void failed!");
                    // resultText.setTextColor(getColor(R.color.colorCancel));
                }
            }
        }
    }
    public void performPayment(){
        EcrJsonReq ecrJsonReq = new EcrJsonReq();
        ecrJsonReq.header = new EcrJsonReq.Header();
        ecrJsonReq.request = new EcrJsonReq.Request();
        ecrJsonReq.request.financial = new EcrJsonReq.Financial();
        //ecrJsonReq.request.financial.id = new EcrJsonReq.Id();
        ecrJsonReq.request.financial.transaction = EcrRequestTransactionType.sale;
        ecrJsonReq.request.financial.amounts = new EcrJsonReq.Amounts();
        ecrJsonReq.request.financial.amounts.currencyCode = "RSD";
        ecrJsonReq.request.financial.amounts.base = formatAmount(billTotal, false);
        ecrJsonReq.request.financial.options = new EcrJsonReq.Options();
        ecrJsonReq.request.financial.options.language = "sr";
        ecrJsonReq.request.financial.options.print = "true";

        String tempRequest = "\"request\":"+new Gson().toJson(ecrJsonReq.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        ecrJsonReq.header.version = "01";
        ecrJsonReq.header.length = tempRequest.length();
        ecrJsonReq.header.hash = generatedSHA512;

        String jsonRequest = new Gson().toJson(ecrJsonReq);
        Log.d("ECR", "Request: " + jsonRequest);

        TransactionData newTrans = new TransactionData();
        newTrans.transaction = ecrJsonReq.request.financial.transaction;
        newTrans.base = ecrJsonReq.request.financial.amounts.base;
        newTrans.currencyCode = ecrJsonReq.request.financial.amounts.currencyCode;
        newTrans.pending = true;
        newTrans.voided = false;
        MyApp.transactionList.add(newTrans);

        ecrAction = ECR_TRANSACTION;

        try {
            sendJsonStringToApos(jsonRequest, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doEcr(String json, boolean fromAssets){
        executor.execute(()->{
            try {


                if (paytenAidlInterface != null) {
                    String resultData = paytenAidlInterface.ecrResponse(json);
                    handler.post(() -> {
                        Log.d("ECR", "Response received: " + resultData);
                        resp = processEcrResponse(resultData);
                        //returnToMainScreen();
                    });
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    EcrJsonRsp processEcrResponse(String json){
        try {
            return new Gson().fromJson(json, EcrJsonRsp.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void sendJsonStringToApos(String json, boolean fromAssets) throws RemoteException, InterruptedException {
        if(!serviceOrPayment){
            // Send action to service
            doEcr(json, fromAssets);
        }
        else
        {
            // Send request to payment application
            if(android10Spec)
            {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(new ComponentName("com.payten.paytenapos", "com.payten.paytenapos.ui.activities.SplashActivity"));
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }

            Intent intent = new Intent("com.payten.ecr.action");
            intent.setPackage("com.payten.paytenapos");

            intent.putExtra("ecrJson", json);

            intent.putExtra("senderIntentFilter", "senderIntentFilter");
            intent.putExtra("senderPackage", getPackageName());
            intent.putExtra("senderClass", "com.payten.ecrdemo.MainActivity");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            getApplicationContext().sendBroadcast(intent);
        }
    }
    private void setupNavigation() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_insurancePackets)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    void returnToMainScreen(){
        mainScreen.setVisibility(View.VISIBLE);
        resultScreen.setVisibility(View.GONE);
        systemScreen.setVisibility(View.GONE);

        if (resultScreenTimer != null) {
            resultScreenTimer.cancel();
        }

        billTotal = BigDecimal.ZERO;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    String formatAmount(BigDecimal d, boolean withCurrency){
        String s = String.valueOf(d);
        int decimalPosition = s.indexOf('.');
        if (decimalPosition < 0){
            // No decimals point
            s = s + ".00";
        }
        else if (decimalPosition == s.length() - 2) {
            // Only one decimal
            s = s + "0";
        }
        else if (decimalPosition == s.length() - 1) {
            // decimal point at the end without digits to follow
            s = s + "00";
        }
        if (withCurrency){
            s = s + " " + "RSD";
        }
        return s;
    }


}