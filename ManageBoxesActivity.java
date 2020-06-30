package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageBoxesActivity extends AppCompatActivity {

    String userID;
    String name;

    DatabaseHelper db;

    RecyclerView recyclerView;
    ArrayList<String> box_name, box_price, box_withdrawal;
    CustomAdapter adapter;

    TextView tvLoggedName;
    Button btnCreateNewBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_boxes);

        userID = getIntent().getStringExtra("USER_ID");

        db = new DatabaseHelper(this);

        Cursor cursor = db.getUserData();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(0).equals(userID)){
                name = cursor.getString(4) + " " + cursor.getString(5);
            }
        }

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);
        tvLoggedName.setText(name);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_manage);
        box_name = new ArrayList<>();
        box_price = new ArrayList<>();
        box_withdrawal = new ArrayList<>();

        storeDataInArrays();

        adapter = new CustomAdapter(ManageBoxesActivity.this, box_name, box_price, box_withdrawal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageBoxesActivity.this));


        btnCreateNewBox = (Button) findViewById(R.id.btn_create_new_box);
        btnCreateNewBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageBoxesActivity.this, CreateBoxesActivity.class);
                intent.putExtra("USER_ID", userID);
                startActivity(intent);
            }
        });

        /*
        btnDeleteDebug = (Button) findViewById(R.id.delete_debugger);
        btnDeleteDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = db.deleteData("4");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
         */
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
        StringBuffer buffer = new StringBuffer();
        /*while (res.moveToNext()) {
            buffer.append("Id: " + res.getString(0)+"\n");
            buffer.append("Name: " + res.getString(1)+"\n");
            buffer.append("Desc: " + res.getString(2)+"\n");
            buffer.append("Price: " + res.getString(3)+"\n");
            buffer.append("Quant: " + res.getString(4)+"\n");
            buffer.append("uTime: " + res.getString(5)+"\n");
            buffer.append("wTime: " + res.getString(6)+"\n");
            buffer.append("CID: " + res.getString(7)+"\n");
        }*/
    }

    void storeDataInArrays() {
        Cursor cursor = db.getBoxData();
        if(cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                if(cursor.getString(7).equals(userID)) {
                    box_name.add(cursor.getString(1));
                    box_price.add("$"+cursor.getString(3));
                    box_withdrawal.add("Withdrawal: " + cursor.getString(6));
                }
            }
        }
    }
}
