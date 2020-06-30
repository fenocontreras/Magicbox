package com.kumiho.magicbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int[] images = {R.drawable.tacobell};
    String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.company_recycler);

        CompanyAdapter companyAdapter = new CompanyAdapter(this, images);
        recyclerView.setAdapter(companyAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

}
