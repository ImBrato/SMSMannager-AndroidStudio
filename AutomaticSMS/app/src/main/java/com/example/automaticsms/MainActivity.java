package com.example.automaticsms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

    private EditText editTextPhoneNumber;

//    Random random = new Random();
    int min = 1000;
    int max = 9999;
    public int randomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPhoneNumber =(EditText) findViewById(R.id.editTextPhoneNumber);
        Button buttonVerify =(Button) findViewById(R.id.buttonVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                randomNumber = (random.nextInt(max - min + 1 ) + min);
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                sendVerificationCode(phoneNumber, randomNumber);
                Intent intent = new Intent(MainActivity.this, newActivity.class);
                intent.putExtra("key", randomNumber);
                startActivity(intent);
            }
        });


    }

    private void sendVerificationCode(String phoneNumber, int randomNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SEND_SMS);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Mã xác minh là " + randomNumber, null, null);
            Toast.makeText(this, "Verification code sent!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                sendVerificationCode(phoneNumber, 1);
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
