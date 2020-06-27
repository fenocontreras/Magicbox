package com.kumiho.magicbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    Context ct;
    int img[];

    public CompanyAdapter(Context context, int images[]){
        ct = context;
        img = images;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.company_row, viewGroup, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder companyViewHolder, int i) {
        companyViewHolder.companyImage.setImageResource(img[i]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyImage;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyImage = itemView.findViewById(R.id.companyImageView);
        }

    }
}
