package com.yousef.el_mufassir.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.adapter.AyatAdapter;
import com.yousef.el_mufassir.databinding.ActivityAyatBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.interfaces.RecyclerViewListener;
import com.yousef.el_mufassir.model.Constants;

public class AyatActivity extends AppCompatActivity implements RecyclerViewListener {

    private int numberOfSoura;
    private Repository repository;
    private ActivityResultLauncher<String> activityResultLauncher;
    private Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityAyatBinding binding= ActivityAyatBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        repository=new Repository(this);
        constants=Constants.newInstance();

        numberOfSoura=getIntent().getExtras().getInt( constants.NUMBER );
        int numOfOpenAya=getIntent().getExtras().getInt( constants.LOCK);
        binding.toolbar.setTitle(getString(R.string.soura)+" "+repository.getName()[numberOfSoura]);



        AyatAdapter ayatAdapter=new AyatAdapter( getNumOfAyat(),numOfOpenAya,this );
        GridLayoutManager gridLayoutManager=new GridLayoutManager( this,3  );
        binding.recyclerViewAya.setLayoutManager( gridLayoutManager );
        binding.recyclerViewAya.setAdapter( ayatAdapter );

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result)
                        repository.callPhone();
                    else
                        TastyToast.makeText(getApplicationContext(), getString( R.string.noPermissionCall ), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.whatsapp)
            repository.whatsapp();
        else if (item.getItemId()==R.id.about){
            BottomSheetDialog bottomSheetDialogSoura=new BottomSheetDialog( this,R.style.bottomTheme );
            View bottom= LayoutInflater.from( this ).inflate( R.layout.bottom_about,findViewById( R.id.containerBottom ) );
            bottomSheetDialogSoura.setContentView( bottom );
            ImageButton callMe=bottom.findViewById( R.id.callMe );
            callMe.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                    repository.callPhone();
                else
                    activityResultLauncher.launch(Manifest.permission.CALL_PHONE);
            });
            bottomSheetDialogSoura.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClickItem(int visible, int position) {
        if(visible==View.VISIBLE){
            TastyToast.makeText( this, getString(R.string.noOpenAya)+" "+position,TastyToast.LENGTH_LONG,TastyToast.ERROR ).show();
        }
        else {
            Intent intent = new Intent( this, TafseerActivity.class );
            intent.putExtra( constants.NUMBER, numberOfSoura+1);
            intent.putExtra( constants.AYA, position);
            startActivity( intent );
        }
    }

    String[] getNumOfAyat(){
        int index=Integer.parseInt(repository.getAyat()[numberOfSoura]);
        String[] Ayat=new String[index];
        for(int i=0;i<index;i++)
            Ayat[i]=getResources().getStringArray(R.array.Number2)[i+1];
        return Ayat;
    }
}