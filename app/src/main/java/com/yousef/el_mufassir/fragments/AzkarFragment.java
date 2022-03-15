package com.yousef.el_mufassir.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.activity.AzkarActivity;
import com.yousef.el_mufassir.adapter.AzkarAdapter;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.interfaces.RecyclerViewAzkar;
import com.yousef.el_mufassir.model.Constants;


public class AzkarFragment extends Fragment implements RecyclerViewAzkar {



    private Constants constants;
    private AzkarAdapter azkarAdapter;
    private LinearLayoutManager linearLayoutManager;

    public AzkarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository repository=new Repository(getContext());
        constants= Constants.newInstance();
        azkarAdapter = new AzkarAdapter( repository.getAzkar(), repository.getCountAzkar(), this );
        linearLayoutManager = new LinearLayoutManager( getContext() );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_azkar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewAzkar = view.findViewById(R.id.recyclerViewAzkar);
        recyclerViewAzkar.setLayoutManager(linearLayoutManager);
        recyclerViewAzkar.setAdapter(azkarAdapter);
    }

    @Override
    public void clickItem(int position) {
        Intent intent=new Intent(getContext(), AzkarActivity.class);
        intent.putExtra(constants.NUMBER,position);
        startActivity(intent);
    }
}