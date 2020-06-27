package com.kumiho.magicbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    ImageView imgLogo;
    EditText etUsername, etPassword;
    Button btnLogIn, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setImageResource(R.drawable.ic_launcher_background);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogIn = (Button) findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean res = db.checkUser(username, password);
                if (res) {
                    Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("USER_ID", db.getUserID(username));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}
