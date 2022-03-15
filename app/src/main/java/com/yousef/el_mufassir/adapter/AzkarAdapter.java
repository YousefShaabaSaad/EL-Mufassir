package com.yousef.el_mufassir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yousef.el_mufassir.R;
import java.util.List;

public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.AzkarHolder>{

    private final List<String> azkars;
    private final List<Integer> counts;
    public AzkarAdapter(List<String> azkars, List<Integer> counts){
        this.azkars=azkars;
        this.counts=counts;
    }
    @NonNull
    @Override
    public AzkarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_azkar,parent,false);
        return new AzkarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarHolder holder, int position) {
        String zekr=azkars.get(position);
        int count=counts.get(position);
        holder.azkar.setText(zekr);
        holder.count.setText(count);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return azkars.size();
    }

    static class AzkarHolder extends RecyclerView.ViewHolder{
        TextView count, azkar;
        public AzkarHolder(@NonNull View itemView) {
            super(itemView);
            count= itemView.findViewById(R.id.timer);
            azkar= itemView.findViewById(R.id.azkar);
        }
    }
}
