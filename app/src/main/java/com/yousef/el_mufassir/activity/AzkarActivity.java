package com.yousef.el_mufassir.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.snackbar.Snackbar;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.databinding.ActivityAzkarBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;
import java.util.Objects;

public class AzkarActivity extends AppCompatActivity {
    private int count, newCount=0, position;
    private Repository repository;
    private ActivityResultLauncher<String> activityResultLauncher;
    private ActivityAzkarBinding binding;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAzkarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getStringArray(R.array.Fragments)[1]);

        Constants constants=Constants.newInstance();
        repository=new Repository(this);
        position= getIntent().getExtras().getInt(constants.NUMBER);
        key=constants.COUNT+position;
        count=repository.returnInt(key,0);

        binding.timer.setText(String.valueOf(count));
        binding.azkar.setText(getResources().getStringArray(R.array.Azkar)[position]);


        MediaPlayer player = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        binding.azkar.setOnClickListener(v -> {
            count++;
            repository.saveInt(key,count);
            binding.timer.setText(String.valueOf(count));
            if(count%10==0) {
                player.start();
            }
        });

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result)
                        repository.callPhone();
                    else
                        TastyToast.makeText(this, getString( R.string.noPermissionCall ), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.azkar, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.whatsapp)
            repository.whatsapp();
        else if (item.getItemId()==R.id.about)
            repository.about(findViewById( R.id.containerBottom ),activityResultLauncher);
        else if (item.getItemId()==R.id.delete){
            newCount=count;
            count=0;
            binding.timer.setText(String.valueOf(count));
            repository.saveInt(key,count);

            Snackbar snackbar=Snackbar.make(binding.azkar,getString(R.string.countDelete)+" "+getResources().getStringArray(R.array.Azkar)[position],Snackbar.LENGTH_LONG);
            snackbar.setAction("تراجع", v1 -> {
                count=newCount;
                binding.timer.setText(String.valueOf(count));
                repository.saveInt(key,count);
            });
            snackbar.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}