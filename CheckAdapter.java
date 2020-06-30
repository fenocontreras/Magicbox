package com.kumiho.magicbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.MyViewHolder> {

    Context context;
    ArrayList box_name, box_price, box_withdrawal, box_quantity, box_company;
    OnBoxListener onBoxListener;

    CheckAdapter(Context context, ArrayList box_name, ArrayList box_price, ArrayList box_withdrawal, ArrayList box_quantity, ArrayList box_company, OnBoxListener onBoxListener) {
        this.context = context;
        this.box_name = box_name;
        this.box_price = box_price;
        this.box_withdrawal = box_withdrawal;
        this.box_quantity = box_quantity;
        this.box_company = box_company;
        this.onBoxListener = onBoxListener;
    }

    @NonNull
    @Override
    public CheckAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.check_row, viewGroup, false);
        return new MyViewHolder(view, onBoxListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBoxName.setText(String.valueOf(box_name.get(i)));
        myViewHolder.tvBoxPrice.setText(String.valueOf(box_price.get(i)));
        myViewHolder.tvBoxTime.setText(String.valueOf(box_withdrawal.get(i)));
        myViewHolder.tvBoxQuantity.setText(String.valueOf(box_quantity.get(i)));
        myViewHolder.tvBoxCompany.setText(String.valueOf(box_company.get(i)));
    }

    @Override
    public int getItemCount() {
        return box_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvBoxName, tvBoxPrice, tvBoxTime, tvBoxQuantity, tvBoxCompany;
        OnBoxListener onBoxListener;

        public MyViewHolder(@NonNull View itemView, OnBoxListener onBoxListener) {
            super(itemView);
            tvBoxName = itemView.findViewById(R.id.row_box_name);
            tvBoxPrice = itemView.findViewById(R.id.row_box_price);
            tvBoxTime = itemView.findViewById(R.id.row_box_time);
            tvBoxQuantity = itemView.findViewById(R.id.row_box_quantity);
            tvBoxCompany = itemView.findViewById(R.id.row_box_company);
            this.onBoxListener = onBoxListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBoxListener.OnBoxClick(getAdapterPosition());
        }
    }

    public interface OnBoxListener{
        void OnBoxClick (int position);
    }
}
