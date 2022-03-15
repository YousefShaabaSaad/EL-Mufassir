package com.yousef.el_mufassir.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.databinding.ActivityAzkarBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;
import java.util.Objects;

public class AzkarActivity extends AppCompatActivity {

    private int count, newCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAzkarBinding binding=ActivityAzkarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants constants=Constants.newInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getStringArray(R.array.Fragments)[1]);

        Repository repository=new Repository(this);
        int position= getIntent().getExtras().getInt(constants.NUMBER);
        String key=constants.COUNT+position;
        count=repository.returnInt(key,0);

        binding.timer.setText(String.valueOf(count));
        binding.azkar.setText(getResources().getStringArray(R.array.Azkar)[position]);



        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCount=count;
                count=0;
                binding.timer.setText(String.valueOf(count));
                repository.saveInt(key,count);

                Snackbar snackbar=Snackbar.make(v,getString(R.string.countDelete)+" "+getResources().getStringArray(R.array.Azkar)[position],Snackbar.LENGTH_LONG);
                snackbar.setAction("تراجع", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count=newCount;
                        binding.timer.setText(String.valueOf(count));
                        repository.saveInt(key,count);
                    }
                });
                snackbar.show();
            }
        });

        binding.azkar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                repository.saveInt(key,count);
                binding.timer.setText(String.valueOf(count));
            }
        });
    }
}