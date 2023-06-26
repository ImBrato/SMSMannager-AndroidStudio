package com.example.telephony;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    private static final int REQUEST_CALL_PHONE = 1;

    private TextView textViewPhoneNumber;
    private EditText phoneNumber;
    private Button btnCall;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(phoneNumber.getText().toString());
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_READ_PHONE_STATE);
            } else {
                getPhoneNumber();
            }
        } else {
            getPhoneNumber();
        }
    }

//    private void getPhoneNumber() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//
//
//        if (telephonyManager != null) {
//            @SuppressLint("MissingPermission") String phoneNumber = telephonyManager.getLine1Number();
//
//            textViewPhoneNumber.setText("Phone Number: " + phoneNumber + "\nIMEI: " );
//
//        }
//    }

    private void getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            @SuppressLint("MissingPermission") String phoneNumber = telephonyManager.getLine1Number();


//            String imei = telephonyManager.getImei();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String networkTypeString = getNetworkTypeString(telephonyManager.getNetworkType());
            String simStateString = getSimStateString(telephonyManager.getSimState());
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            String networkOperator = telephonyManager.getNetworkOperator();
            String simCountryIso = telephonyManager.getSimCountryIso();

            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("Phone Number: ").append(phoneNumber).append("\n");
//            infoBuilder.append("IMEI: ").append(imei).append("\n");
            infoBuilder.append("Network Operator Name: ").append(networkOperatorName).append("\n");
            infoBuilder.append("Network Operator: ").append(networkOperator).append("\n");
            infoBuilder.append("SIM Country ISO: ").append(simCountryIso).append("\n");
            infoBuilder.append("SIM State: ").append(simStateString).append("\n");
            infoBuilder.append("Network Type: ").append(networkTypeString).append("\n");

            textViewPhoneNumber.setText(infoBuilder.toString());
        }
    }


    private String getNetworkTypeString(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_NR:
                return "5G NR";
            default:
                return "Unknown";
        }
    }
    private String getSimStateString(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "Unknown";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "Absent";
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "PIN Required";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "PUK Required";
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "Network Locked";
            case TelephonyManager.SIM_STATE_READY:
                return "Ready";
            default:
                return "Unknown";
        }
    }
    private void makePhoneCall(String PhoneNumber){

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_PHONE_STATE || requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoneNumber();
                makePhoneCall(phoneNumber.getText().toString());
            }
        }
    }

}
