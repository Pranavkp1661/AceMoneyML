package com.pranav.acemoneyml.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.pranav.acemoneyml.R;
import com.pranav.acemoneyml.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static final Integer HANDLER_TIME = 3000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            finish();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }, HANDLER_TIME);
    }
}