package com.kumiho.magicbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    Switch swIsCompany;
    EditText etName, etSurname, etEmail, etUsername, etPassword, etPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.et_enter_name);
        etSurname = (EditText) findViewById(R.id.et_enter_surname);
        etEmail = (EditText) findViewById(R.id.et_enter_email);
        etUsername = (EditText) findViewById(R.id.et_enter_username);
        etPassword = (EditText) findViewById(R.id.et_enter_password);
        etPassword2 = (EditText) findViewById(R.id.et_confirm_password);

        swIsCompany = (Switch) findViewById(R.id.sw_is_company);
        swIsCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etSurname.setText("");
                    etSurname.setEnabled(false);
                }
                else {
                    etSurname.setEnabled(true);
                }
            }
        });

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String surname = etSurname.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();
                int isCompany;
                if (swIsCompany.isChecked())
                    isCompany = 1;
                else
                    isCompany = 0;

                // Write better validation later

                if (password.equals(password2)) {
                    boolean res = db.addUser(username, password, isCompany, name, surname, email);
                    if (res) {
                        Toast.makeText(RegisterActivity.this, "You have registered.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "There is a problem with your data. Check it", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    etPassword.setText("");
                    etPassword2.setText("");
                    Toast.makeText(RegisterActivity.this, "Passwords don't match. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
