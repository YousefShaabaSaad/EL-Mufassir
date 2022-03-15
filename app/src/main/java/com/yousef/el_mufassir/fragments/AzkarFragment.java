package com.yousef.el_mufassir.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.activity.AzkarActivity;
import com.yousef.el_mufassir.adapter.AzkarAdapter;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.interfaces.RecyclerViewAzkar;
import com.yousef.el_mufassir.model.Constants;

import java.util.ArrayList;
import java.util.List;


public class AzkarFragment extends Fragment implements RecyclerViewAzkar {



    private Constants constants;
    private AzkarAdapter azkarAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> azkars;
    private List<Integer> counts;
    private Repository repository;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public AzkarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository=new Repository(getContext());
        constants= Constants.newInstance();
        azkars=new ArrayList<>();
        counts=new ArrayList<>();

        azkars=repository.getAzkar();
        counts=repository.getCountAzkar();
        azkarAdapter = new AzkarAdapter( azkars, counts, this );
        linearLayoutManager = new LinearLayoutManager( getContext() );
        Toast.makeText(getContext(),"no",Toast.LENGTH_LONG).show();
        activityResultLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Toast.makeText(getContext(), "Yes", Toast.LENGTH_LONG).show();
                    }
                });
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
        activityResultLauncher.launch(intent);
    }
}