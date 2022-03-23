package com.yousef.el_mufassir.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.databinding.ActivityTafseerBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;
import com.yousef.el_mufassir.model.Tafseer;

import java.util.Objects;

public class TafseerActivity extends AppCompatActivity {

    private Repository repository;
    private ActivityResultLauncher<String> activityResultLauncher,activityResultLauncher2;
    private ActivityTafseerBinding binding;
    private String fileNameAya;
    private String fileNameTafseer;
    private String appName;
    private Tafseer tafseer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTafseerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NotificationManager notificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = getSystemService( NotificationManager.class);
            notificationManager.cancelAll();
        }
        repository=new Repository(this);
        Constants constants=Constants.newInstance();

        int numberOfSoura=getIntent().getExtras().getInt( constants.NUMBER );
        int numberOfAya=getIntent().getExtras().getInt( constants.AYA );
        String nameOfSoura =repository.getName()[numberOfSoura];
        String textTitle = getString(R.string.tafseerSoura)+" "+nameOfSoura+" "+getString(R.string.chooseAya)+" "+getNumAya(numberOfAya);
       Objects.requireNonNull(getSupportActionBar()).setTitle(textTitle);

        tafseer=new Tafseer();

        binding.nameSoura.setText(nameOfSoura);
        tafseer=repository.getAyaAndTafseer(numberOfSoura,numberOfAya-1);
        String textAya=tafseer.getAyaText()+" "+getNumAyaShow(numberOfAya);
        binding.ayaText.setText(textAya);
        binding.tafseerText.setText(tafseer.getTafseerText());

        fileNameAya=getString(R.string.soura)+" "+nameOfSoura+" "+getString(R.string.aya)+" "+getNumAya(numberOfAya);
        fileNameTafseer=getString(R.string.tafseerSoura)+" "+nameOfSoura+" "+getString(R.string.aya)+" "+getNumAya(numberOfAya);
        appName=getString(R.string.appName3);

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result){
                        repository.getImageTafseer(binding.layoutView, fileNameAya);
                        TastyToast.makeText(this,getString(R.string.successDownload),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
                    }
                    else
                        TastyToast.makeText(getApplicationContext(), getString( R.string.noPermission ), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );

        activityResultLauncher2=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result){
                        repository.getImageTafseer(binding.layoutView2, fileNameTafseer);
                        TastyToast.makeText(this,getString(R.string.successDownload),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
                    }
                    else
                        TastyToast.makeText(getApplicationContext(), getString( R.string.noPermission ), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );
        binding.whatsapp.setOnClickListener(v -> repository.whatsapp());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tafseer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.copyAya) {
            repository.getTextTafseer(tafseer.getAyaText()+"\n\n"+fileNameAya+"♥️\n "+appName);
            TastyToast.makeText(this,getString(R.string.successCopy),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        }
        else if (itemId == R.id.downloadAya) {
           activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        else if (itemId == R.id.copyTafseer) {
            repository.getTextTafseer(tafseer.getTafseerText()+"\n\n"+fileNameTafseer+"♥️\n "+appName);
            TastyToast.makeText(this,getString(R.string.successCopy),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        }
        else if (itemId == R.id.downloadTafseer) {
            activityResultLauncher2.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return super.onOptionsItemSelected(item);
    }


    String getNumAya(int number){
        return getResources().getStringArray(R.array.Number2)[number];
    }

    String getNumAyaShow(int number){
        String num1=getResources().getStringArray(R.array.Number2)[number];
        StringBuilder num2= new StringBuilder();
        for(int i=num1.length()-1;i>=0;i--){
            num2.append(num1.charAt(i));
        }
        return num2.toString();
    }
}