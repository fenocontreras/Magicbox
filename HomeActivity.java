package com.kumiho.magicbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView tvLoggedName;
    TextView tvViewBoxes;
    //String userID = getIntent().getStringExtra("USER_ID");
    boolean isCompany = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);

        tvViewBoxes = (TextView) findViewById(R.id.tv_view_boxes);
        if (isCompany) {
            tvViewBoxes.setText(getResources().getString(R.string.manage_boxes));
        }
        else {
            tvViewBoxes.setText(getResources().getString(R.string.check_boxes));
        }
        tvViewBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompany) {
                    Intent intent = new Intent(HomeActivity.this, ManageBoxesActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(HomeActivity.this, CheckBoxesActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
