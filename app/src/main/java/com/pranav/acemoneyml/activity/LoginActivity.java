package com.pranav.acemoneyml.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.pranav.acemoneyml.R;
import com.pranav.acemoneyml.database.RoomDataBase;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {
    TextView tvNewReg;
    EditText etEmailLogin;
    EditText etPassword;
    Button btLogin;
    Context context;
    String email;
    String pass;
    final AtomicBoolean var = new AtomicBoolean(true);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        initActivity();
        tvNewReg.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegistrationActivity.class);
            startActivity(intent);
        });
        btLogin.setOnClickListener(v -> {
            email = etEmailLogin.getText().toString().trim();
            pass = etPassword.getText().toString().trim();
            if (email.equals("")) {
                etEmailLogin.setError("Enter your Email");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmailLogin.setError("Enter a valid Email Address");
            } else if (pass.equals("")) {
                etPassword.setError("Enter your Email");
            } else if (pass.length() <= 6) {
                etPassword.setError("Password should be greater than 6 characters ");
            } else {
                int count = RoomDataBase.getInstance(getApplicationContext()).mainDao().getLoginData(email, pass);
                if (count == 0) {
                    Toast.makeText(context, "Login Error Invalid Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
        etPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                // your action here
                if (var.get()) {
                    var.set(false);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_baseline_remove_red_eye_24), null);
                } else {
                    var.set(true);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off_24), null);
                }
                return true;
            }
            return false;
        });
    }

    private void initActivity() {
        tvNewReg = findViewById(R.id.tvNewReg);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
    }
}