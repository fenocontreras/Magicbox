package com.kumiho.magicbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList box_name, box_price, box_withdrawal;

    CustomAdapter(Context context, ArrayList box_name, ArrayList box_price, ArrayList box_withdrawal) {
        this.context = context;
        this.box_name = box_name;
        this.box_price = box_price;
        this.box_withdrawal = box_withdrawal;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.manage_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvBoxName.setText(String.valueOf(box_name.get(i)));
        myViewHolder.tvBoxPrice.setText(String.valueOf(box_price.get(i)));
        myViewHolder.tvBoxTime.setText(String.valueOf(box_withdrawal.get(i)));
    }

    @Override
    public int getItemCount() {
        return box_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBoxName, tvBoxPrice, tvBoxTime, tvBoxEdit;
        ImageView imgBoxErase;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBoxName = itemView.findViewById(R.id.tv_row_box_name);
            tvBoxPrice = itemView.findViewById(R.id.tv_row_box_price);
            tvBoxTime = itemView.findViewById(R.id.tv_row_box_time);
            tvBoxEdit = itemView.findViewById(R.id.tv_row_box_edit);
            imgBoxErase = itemView.findViewById(R.id.img_row_box_erase);
        }
    }
}
