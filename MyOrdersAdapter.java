package com.kumiho.magicbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {

    private Context context;
    private ArrayList  box_name, box_price, box_withdrawal, box_company;

    MyOrdersAdapter(Context context, ArrayList box_name, ArrayList box_price, ArrayList box_withdrawal, ArrayList box_company){
        this.context = context;
        this.box_name = box_name;
        this.box_price = box_price;
        this.box_withdrawal = box_withdrawal;
        this.box_company = box_company;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_orders_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBoxName.setText(String.valueOf(box_name.get(i)));
        myViewHolder.tvBoxPrice.setText(String.valueOf(box_price.get(i)));
        myViewHolder.tvBoxTime.setText(String.valueOf(box_withdrawal.get(i)));
        myViewHolder.tvBoxCompany.setText(String.valueOf(box_company.get(i)));
    }

    @Override
    public int getItemCount() {
        return box_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBoxName, tvBoxPrice, tvBoxTime, tvBoxCompany;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBoxName = itemView.findViewById(R.id.tv_box_name);
            tvBoxPrice = itemView.findViewById(R.id.tv_box_price);
            tvBoxTime = itemView.findViewById(R.id.tv_box_time);
            tvBoxCompany = itemView.findViewById(R.id.tv_box_company);
        }
    }
}
