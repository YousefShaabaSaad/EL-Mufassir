package com.yousef.el_mufassir.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.interfaces.RecyclerViewListener;


public class SouraAdapter extends RecyclerView.Adapter<SouraAdapter.SouraHolder> {
    private final String[] Names;
    private final String[] Ayat;
    private final int numOfOpenSoura;
    private final RecyclerViewListener recyclerViewListener;


    public SouraAdapter( String[] Names, String[] Ayat,int numOfOpenSoura, RecyclerViewListener recyclerViewListener) {
        this.Names=Names;
        this.Ayat=Ayat;
        this.numOfOpenSoura=numOfOpenSoura;
        this.recyclerViewListener=recyclerViewListener;
    }

    @Override
    public int getItemCount() {
        return Names.length;
    }

    @NonNull
    @Override
    public SouraHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_soura, parent, false );
        return new SouraHolder( view );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SouraHolder holder, int position) {
        String name = Names[position];
        String aya = Ayat[position];
        holder.souraName.setText( name );
        holder.numberOfAyat.setText( aya );
        if(position<numOfOpenSoura)
            holder.lockSoura.setVisibility( View.GONE );
        else
            holder.lockSoura.setVisibility( View.VISIBLE );

        holder.itemView.setOnClickListener(v -> recyclerViewListener.onClickItem(holder.lockSoura.getVisibility(),position));
    }

    static class SouraHolder extends RecyclerView.ViewHolder {
        TextView  souraName, numberOfAyat;
        ImageView lockSoura;
        SouraHolder(View itemView) {
            super( itemView );
            souraName = itemView.findViewById( R.id.souraName );
            numberOfAyat = itemView.findViewById( R.id.numberOfAyat );
            lockSoura = itemView.findViewById( R.id.lockSoura );
        }
    }
}
