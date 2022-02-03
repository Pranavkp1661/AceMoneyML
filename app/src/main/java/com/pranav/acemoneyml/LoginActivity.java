package com.pranav.acemoneyml;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView tvNewReg;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        tvNewReg = findViewById(R.id.tvNewReg);
        tvNewReg.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}