package com.kumiho.magicbox;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayBoxActivity extends AppCompatActivity {

    DatabaseHelper db;

    String userID;
    String name;
    String boxName;

    String boxID;
    String description;
    String quantity;
    String price;
    String uploaded;
    String withdrawal;
    String companyID;
    String companyName;

    TextView tvLoggedName, tvBoxName;

    TextView tvCompanyName, tvDescription, tvQuantity, tvPrice, tvUploaded, tvWithdrawal;

    ImageView imgBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_box);

        userID = getIntent().getStringExtra("USER_ID");
        boxName = getIntent().getStringExtra("BOX_NAME");

        db = new DatabaseHelper(this);

        Cursor cursor = db.getUserData();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(0).equals(userID)){
                name = cursor.getString(4) + " " + cursor.getString(5);
            }
        }
        cursor.close();

        Cursor box_Cursor = db.getBoxData();
        for(box_Cursor.moveToFirst(); !box_Cursor.isAfterLast(); box_Cursor.moveToNext()) {
            if (box_Cursor.getString(1).equals(boxName)) {
                boxID = box_Cursor.getString(0);
                description = box_Cursor.getString(2);
                price = box_Cursor.getString(3);
                quantity = box_Cursor.getString(4);
                uploaded = box_Cursor.getString(5);
                withdrawal = box_Cursor.getString(6);
                companyID = box_Cursor.getString(7);
                Cursor userCursor = db.getUserData();
                for(userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                    if(userCursor.getString(0).equals(companyID)){
                        companyName = userCursor.getString(4);
                    }
                }
                userCursor.close();
            }
        }
        box_Cursor.close();

        tvBoxName = (TextView) findViewById(R.id.tv_home_title);
        tvBoxName.setText(boxName);

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);
        tvLoggedName.setText(name);

        tvCompanyName = (TextView) findViewById(R.id.tv_company);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvQuantity = (TextView) findViewById(R.id.tv_quantity);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvUploaded = (TextView) findViewById(R.id.tv_uploaded);
        tvWithdrawal = (TextView) findViewById(R.id.tv_withdrawal);

        tvCompanyName.setText(companyName);
        tvDescription.setText(description);
        tvQuantity.setText(quantity);
        tvPrice.setText("$" + price);
        tvUploaded.setText(uploaded);
        tvWithdrawal.setText(withdrawal);

        imgBuy = (ImageView) findViewById(R.id.img_buy);
        imgBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = db.addOrder(Integer.parseInt(boxID), Integer.parseInt(userID));
                if (res) {
                    finish();
                }
                else {
                    Toast.makeText(DisplayBoxActivity.this, "There was an error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
