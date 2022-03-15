package com.yousef.el_mufassir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.interfaces.RecyclerViewAzkar;


public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.AzkarHolder>{

    private final String[] azkars;
    private final int[] counts;

    private final RecyclerViewAzkar recyclerViewAzkar;
    public AzkarAdapter(String[] azkars, int[] counts, RecyclerViewAzkar recyclerViewAzkar){
        this.azkars=azkars;
        this.counts=counts;
        this.recyclerViewAzkar=recyclerViewAzkar;
    }
    @NonNull
    @Override
    public AzkarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_azkar,parent,false);
        return new AzkarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarHolder holder, int position) {
        String zekr=azkars[position];
        int c=counts[position];
        holder.azkar.setText(zekr);
        holder.count.setText(String.valueOf(c));
        holder.itemView.setOnClickListener(v -> recyclerViewAzkar.clickItem(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return azkars.length;
    }

    static class AzkarHolder extends RecyclerView.ViewHolder{
        TextView count, azkar;
        AzkarHolder(@NonNull View itemView) {
            super(itemView);
            count= itemView.findViewById(R.id.timer);
            azkar= itemView.findViewById(R.id.azkar);
        }
    }
}
