package com.kumiho.magicbox;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    String userID;
    String name;
    TextView tvLoggedName;
    DatabaseHelper db;
    RecyclerView recyclerView;
    MyOrdersAdapter adapter;
    ArrayList<String> box_name, box_price, box_withdrawal, box_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        userID = getIntent().getStringExtra("USER_ID");

        db = new DatabaseHelper(this);

        Cursor cursor = db.getUserData();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(0).equals(userID)){
                name = cursor.getString(4) + " " + cursor.getString(5);
            }
        }
        cursor.close();

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);
        tvLoggedName.setText(name);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_my_orders);
        box_name = new ArrayList<>();
        box_price = new ArrayList<>();
        box_withdrawal = new ArrayList<>();
        box_company = new ArrayList<>();

        storeDataInArrays();

        adapter = new MyOrdersAdapter(MyOrdersActivity.this, box_name, box_price, box_withdrawal, box_company);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this));
    }

    void storeDataInArrays() {
        String boxID;
        String companyID;
        String companyName;
        Cursor cursor = db.getOrderData();
        if(cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                if(cursor.getString(2).equals(userID)) {
                    boxID = cursor.getString(1);
                    Cursor boxCursor = db.getBoxData();
                    if(boxCursor.getCount() != 0) {
                        while (boxCursor.moveToNext()) {
                            if (boxCursor.getString(0).equals(boxID)) {
                                box_name.add(boxCursor.getString(1));
                                box_price.add("$" + boxCursor.getString(3));
                                box_withdrawal.add("Withdrawal: " + boxCursor.getString(6));
                                companyID = boxCursor.getString(7);
                                Cursor userCursor = db.getUserData();
                                while (userCursor.moveToNext()) {
                                    if (userCursor.getString(0).equals(companyID)) {
                                        box_company.add(userCursor.getString(4));
                                    }
                                }
                                userCursor.close();
                            }
                        }
                    }
                    boxCursor.close();
                }
            }

        }
        cursor.close();
    }
}
