package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckBoxesActivity extends AppCompatActivity implements CheckAdapter.OnBoxListener {

    String userID;
    String name;

    DatabaseHelper db;

    RecyclerView recyclerView;
    ArrayList<String> box_name, box_price, box_withdrawal, box_quantity, box_company;
    CheckAdapter adapter;

    TextView tvLoggedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_boxes);

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_check);

        box_name = new ArrayList<>();
        box_price = new ArrayList<>();
        box_withdrawal = new ArrayList<>();
        box_quantity = new ArrayList<>();
        box_company = new ArrayList<>();

        storeDataInArrays();

        adapter = new CheckAdapter(CheckBoxesActivity.this, box_name, box_price, box_withdrawal, box_quantity, box_company, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckBoxesActivity.this));
    }

    void storeDataInArrays() {
        Cursor cursor = db.getBoxData();
        Cursor cursor2 = db.getUserData();
        if(cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                box_name.add(cursor.getString(1));
                box_price.add("$" + cursor.getString(3));
                box_withdrawal.add("Withdrawal: " + cursor.getString(6));
                box_quantity.add(cursor.getString(4));
                for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()){
                    if (cursor.getString(7).equals(cursor2.getString(0))) {
                        box_company.add(cursor2.getString(4));
                    }
                }
            }
        }
        cursor.close();
        cursor2.close();
    }

    @Override
    public void OnBoxClick(int position) {

        Intent intent = new Intent(this, DisplayBoxActivity.class);
        intent.putExtra("BOX_NAME", box_name.get(position));
        intent.putExtra("USER_ID", userID);
        startActivity(intent);
    }
}
