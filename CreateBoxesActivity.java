package com.kumiho.magicbox;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateBoxesActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView tvLoggedName;
    EditText etBoxName, etBoxDescription, etBoxPrice, etBoxQuantity, etBoxTime;
    ImageView imgCreateBox;
    String userID;
    int companyID;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_boxes);

        userID = getIntent().getStringExtra("USER_ID");
        companyID = Integer.parseInt(userID);

        db = new DatabaseHelper(this);

        Cursor cursor = db.getUserData();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(0).equals(userID)){
                name = cursor.getString(4) + " " + cursor.getString(5);
            }
        }

        tvLoggedName = (TextView) findViewById(R.id.tv_logged_name);
        tvLoggedName.setText(name);
        etBoxName = (EditText) findViewById(R.id.et_box_name);
        etBoxDescription = (EditText) findViewById(R.id.et_box_description);
        etBoxPrice = (EditText) findViewById(R.id.et_box_price);
        etBoxQuantity = (EditText) findViewById(R.id.et_box_quantity);
        etBoxTime = (EditText) findViewById(R.id.et_box_withdrawal);

        imgCreateBox = (ImageView) findViewById(R.id.img_create);
        imgCreateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String name = etBoxName.getText().toString().trim();
                String description = etBoxDescription.getText().toString().trim();
                String price = etBoxPrice.getText().toString().trim();
                String quantity = etBoxQuantity.getText().toString().trim();
                String uploadTime = sdf.format(Calendar.getInstance().getTime());
                String withdrawalTime = etBoxTime.getText().toString().trim();

                boolean isInserted = db.addBox(name, description, price, quantity, uploadTime, withdrawalTime, companyID);
                if (isInserted) {
                    Toast.makeText(CreateBoxesActivity.this, "Box created.", Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(CreateBoxesActivity.this, ManageBoxesActivity.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                     */
                    finish();
                }
                else {
                    Toast.makeText(CreateBoxesActivity.this, "There was a problem uploading the box.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
