package com.pranav.acemoneyml;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView tvNewReg;
    EditText etEmailLogin;
    EditText etPassword;
    Button btLogin;
    Context context;
    String email;
    String pass;

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
                if (RoomDataBase.getInstance(getApplicationContext()).mainDao().getLoginData(email, pass) == null) {
                    Toast.makeText(context, "Login Error Invalid Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }

    private void initActivity() {
        tvNewReg = findViewById(R.id.tvNewReg);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
    }
}