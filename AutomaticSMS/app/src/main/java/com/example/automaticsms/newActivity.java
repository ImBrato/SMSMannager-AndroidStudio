package com.example.automaticsms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class newActivity extends AppCompatActivity {

    Button btn;
    TextView verifyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        btn = findViewById(R.id.button);
        verifyCode = findViewById(R.id.editText);
        Intent intent = getIntent();
        int data = intent.getIntExtra("key", 0);
        String numberString = Integer.toString(data);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(numberString);
            }
        });
    }
    public void verifyCode(String numberString){


        if(verifyCode.getText().toString().equals(numberString.toString())){
            Toast.makeText(this, "Mã Hợp Lệ !!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Mã Không Hợp Lệ !!" + numberString, Toast.LENGTH_SHORT).show();
        }
    }

}
