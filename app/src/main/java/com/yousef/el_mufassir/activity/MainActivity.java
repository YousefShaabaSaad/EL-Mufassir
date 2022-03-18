package com.yousef.el_mufassir.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.yousef.el_mufassir.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep( 1000 );
                    Intent intent = new Intent( MainActivity.this, HomeActivity.class );
                    startActivity( intent );
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}