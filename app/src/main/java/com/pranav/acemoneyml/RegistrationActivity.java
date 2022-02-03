package com.pranav.acemoneyml;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    Context context;
    EditText etNameReg;
    EditText etEmailReg;
    EditText etPhoneNumberReg;
    EditText etPasswordReg;
    Button btRegister;
    String name;
    String email;
    String phone;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_registration);
        initActivity();
        btRegister.setOnClickListener(v -> {
            name = etNameReg.getText().toString().trim();
            email = etEmailReg.getText().toString().trim();
            phone = etPhoneNumberReg.getText().toString().trim();
            pass = etPasswordReg.getText().toString().trim();
            if (name.equals("")) {
                etNameReg.setError("Enter your Name");
            } else if (email.equals("")) {
                etEmailReg.setError("Enter your Email");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmailReg.setError("Enter a valid Email Address");
            } else if (phone.equals("")) {
                etPhoneNumberReg.setError("Enter your Phone number");
            } else if (phone.length() < 10) {
                etPhoneNumberReg.setError("Phone number must 10 digits");
            } else if (pass.equals("")) {
                etPasswordReg.setError("Enter your Email");
            } else if (pass.length() <= 6) {
                etPasswordReg.setError("Password should be greater than 6 characters ");
            } else {
                RegistrationEntity registrationEntity = new RegistrationEntity();
                registrationEntity.setName(name);
                registrationEntity.setEmail(email);
                registrationEntity.setPhone(phone);
                registrationEntity.setPassword(pass);
                RoomDataBase.getInstance(getApplicationContext()).mainDao().insert(registrationEntity);
                etEmailReg.setText("");
                etPhoneNumberReg.setText("");
                etPasswordReg.setText("");
                etNameReg.setText("");
                Toast.makeText(context, "data inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initActivity() {
        etNameReg = findViewById(R.id.etNameReg);
        etEmailReg = findViewById(R.id.etEmailReg);
        etPhoneNumberReg = findViewById(R.id.etPhoneNumberReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        btRegister = findViewById(R.id.btRegister);
    }
}