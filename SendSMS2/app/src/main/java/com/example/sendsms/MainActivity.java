package com.example.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Magnifier;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextPhone, editTextMassage;
    Button btnSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMassage = findViewById(R.id.editTextContent);
        btnSent = findViewById(R.id.btnSent);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)==
                        PackageManager.PERMISSION_GRANTED){
                    senSMS();
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            100);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            senSMS();

        }
        else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
        }
    }

    private void senSMS() {
        String phone = editTextPhone.getText().toString();
        String message = editTextMassage.getText().toString();

        if(!phone.isEmpty() && !message.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this,"Gửi tin nhắn thành công", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Hãy nhập thông tin người gửi và nội dung!", Toast.LENGTH_LONG).show();
        }
    }
}