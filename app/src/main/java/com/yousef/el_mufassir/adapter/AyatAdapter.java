package com.yousef.el_mufassir.adapter;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;import com.yousef.el_mufassir.R;import com.yousef.el_mufassir.interfaces.RecyclerViewListener;public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.AyaHolder> {    private final String[] Ayat;    private final int numOfOpen;    private final RecyclerViewListener recyclerViewListener;    public AyatAdapter( String[] Ayat, int numOfOpen, RecyclerViewListener recyclerViewListener) {        this.Ayat=Ayat;        this.numOfOpen=numOfOpen;        this.recyclerViewListener=recyclerViewListener;    }    @Override    public int getItemCount() {        return Ayat.length;    }    @NonNull    @Override    public AyaHolder onCreateViewHolder(ViewGroup parent, int viewType) {        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_aya, parent, false );        return new AyaHolder( view );    }    @Override    public void onBindViewHolder(@NonNull AyaHolder holder, int position) {        String aya = Ayat[position];        holder.numberOfAya.setText( aya );        if(position<numOfOpen)            holder.lockAya.setVisibility( View.GONE );        else            holder.lockAya.setVisibility( View.VISIBLE );        holder.itemView.setOnClickListener(v -> recyclerViewListener.onClickItem(holder.lockAya.getVisibility(),position+1));    }    static class AyaHolder extends RecyclerView.ViewHolder {        TextView  numberOfAya;        ImageView lockAya;        AyaHolder(View itemView) {            super( itemView );            numberOfAya = itemView.findViewById( R.id.numberOfAya );            lockAya = itemView.findViewById( R.id.lockAya );        }    }}