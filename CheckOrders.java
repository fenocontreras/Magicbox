package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckOrders extends AppCompatActivity implements CheckOrdersAdapter.OnOrderListener {

    String userID;
    String name;

    DatabaseHelper db;

    RecyclerView recyclerView;
    ArrayList<String> box_name, box_withdrawal, box_buyer;
    CheckOrdersAdapter adapter;

    TextView tvLoggedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orders);

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_check_orders);

        box_name = new ArrayList<>();
        box_withdrawal = new ArrayList<>();
        box_buyer = new ArrayList<>();

        storeDataInArrays();

        adapter = new CheckOrdersAdapter(CheckOrders.this, box_name, box_withdrawal, box_buyer, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckOrders.this));
    }

    void storeDataInArrays() {
        String boxID;
        String buyerID;
        Cursor boxCursor = db.getBoxData();
        if(boxCursor.getCount() != 0) {
            for(boxCursor.moveToFirst(); !boxCursor.isAfterLast(); boxCursor.moveToNext()){
                if (boxCursor.getString(7).equals(userID)) {
                    boxID = boxCursor.getString(0);
                    Cursor orderCursor = db.getOrderData();
                    if(orderCursor.getCount() != 0) {
                        for(orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
                            if (orderCursor.getString(1).equals(boxID)) {
                                box_name.add(boxCursor.getString(1));
                                box_withdrawal.add("Withdrawal: " + boxCursor.getString(6));
                                buyerID = orderCursor.getString(2);
                                Cursor userCursor = db.getUserData();
                                for(userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()){
                                    if (userCursor.getString(0).equals(buyerID)) {
                                        box_buyer.add(userCursor.getString(4) + " " + userCursor.getString(5));
                                    }
                                }
                                userCursor.close();
                            }
                        }
                    }
                    orderCursor.close();
                }
            }
        }
        boxCursor.close();
    }

    @Override
    public void OnOrderClick(int position) {
        String boxName = box_name.get(position);
        String boxBuyer = box_buyer.get(position);

        String boxID = "-1";
        String buyerID = "-1";

        Cursor boxCursor = db.getBoxData();
        if(boxCursor.getCount() != 0) {
            for (boxCursor.moveToFirst(); !boxCursor.isAfterLast(); boxCursor.moveToNext()) {
                if (boxCursor.getString(1).equals(boxName)) {
                    boxID = boxCursor.getString(0);
                }
            }
        }
        boxCursor.close();

        Cursor userCursor = db.getUserData();
        if(userCursor.getCount() != 0) {
            for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                String buyer = userCursor.getString(4) + " " + userCursor.getString(5);
                if (buyer.equals(boxBuyer)) {
                    buyerID = userCursor.getString(0);
                }
            }
        }
        userCursor.close();

        Integer res = db.deleteOrder(boxID, buyerID);
        Intent intent = getIntent();
        intent.putExtra("USER_ID", userID);
        finish();
        startActivity(intent);
    }
}
