package com.yousef.el_mufassir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yousef.el_mufassir.R;

public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.AzkarHolder>{

    @NonNull
    @Override
    public AzkarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_azkar,parent,false);
        return new AzkarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class AzkarHolder extends RecyclerView.ViewHolder{
        TextView count;
        Button azkar, clear;
        public AzkarHolder(@NonNull View itemView) {
            super(itemView);
            count= itemView.findViewById(R.id.timer);
            azkar= itemView.findViewById(R.id.azkar);
            clear= itemView.findViewById(R.id.clear);
        }
    }
}
