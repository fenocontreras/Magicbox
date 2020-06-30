package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView tvLoggedName;
    TextView tvViewBoxes, tvViewOrders;
    String userID;
    int companyRes;
    String name;
    boolean isCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);

        userID = getIntent().getStringExtra("USER_ID");
        Cursor cursor = db.getUserData();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(0).equals(userID)){
                name = cursor.getString(4) + " " + cursor.getString(5);
                companyRes = cursor.getInt(3);
            }
        }
        cursor.close();
        db.close();

        if (companyRes == 1) {
            isCompany = true;
        }
        else {
            isCompany = false;
        }

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);
        tvLoggedName.setText(name);

        tvViewBoxes = (TextView) findViewById(R.id.tv_view_boxes);
        tvViewOrders = (TextView) findViewById(R.id.tv_view_orders);
        if (isCompany) {
            tvViewBoxes.setText(getResources().getString(R.string.manage_boxes));
            tvViewOrders.setText("Check orders");
        }
        else {
            tvViewBoxes.setText(getResources().getString(R.string.check_boxes));
            tvViewOrders.setText("My orders");
        }
        tvViewBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompany) {
                    Intent intent = new Intent(HomeActivity.this, ManageBoxesActivity.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(HomeActivity.this, CheckBoxesActivity.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            }
        });
        tvViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompany) {
                    Intent intent = new Intent(HomeActivity.this, CheckOrders.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            }
        });
    }
}
