package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    ImageView imgLogo;
    EditText etUsername, etPassword;
    ImageView imgLogIn;
    TextView tvSignUp;
    Button btnLogIn, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        imgLogo = (ImageView) findViewById(R.id.img_logo);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        imgLogIn = (ImageView) findViewById(R.id.img_log_in);
        imgLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean res = db.checkUser(username, password);
                if (res) {
                    String extra = "a";
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                    Cursor cursor = db.getUserData();

                    if (cursor.moveToFirst()) {

                        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                            if (cursor.getString(1).equals(username)) {
                                extra = cursor.getString(0);
                            }
                        }
                    }
                    db.close();

                    intent.putExtra("USER_ID", extra);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}
