package com.example.automaticsms;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    EditText otpnumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we need to ask user permission to auto read sms
        requestsmspermission();
        otpnumber = (EditText)findViewById(R.id.edittext);

        new OTP_Receiver().setEditText(otpnumber);
    }


    private void requestsmspermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this,smspermission);

        //check if read SMS permission is granted or not
        if(grant!= PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0]=smspermission;

            ActivityCompat.requestPermissions(this,permission_list,1);
        }
    }


}