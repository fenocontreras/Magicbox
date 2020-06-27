package com.kumiho.magicbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editDescription, editPrice, editQuantity;
    Button btnCreateBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.et_boxName);
        editDescription = (EditText) findViewById(R.id.et_boxDescription);
        editPrice = (EditText) findViewById(R.id.et_boxPrice);
        editQuantity = (EditText) findViewById(R.id.et_boxQuantity);
        btnCreateBox = (Button) findViewById(R.id.bt_boxCreate);
        CreateBox();
    }

    public void CreateBox() {
        btnCreateBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editDescription.getText().toString(),
                                Float.parseFloat(editPrice.getText().toString()),
                                Integer.parseInt(editQuantity.getText().toString())
                                );
                        if (isInserted)
                            Toast.makeText(MainActivity.this, "Box Created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Box NOT Created", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
