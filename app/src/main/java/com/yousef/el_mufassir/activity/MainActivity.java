package com.yousef.el_mufassir.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.databinding.ActivityMainBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        Repository repository=new Repository(this);

        Constants constants=Constants.newInstance();

        if(repository.returnString( constants.BLOCK,constants.NO ).equals( constants.NO )) {
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
        else if(repository.returnString( constants.BLOCK,constants.NO ).equals( constants.YES )){
            TastyToast.makeText( this,getString( R.string.block ),TastyToast.LENGTH_LONG,TastyToast.WARNING );
        }
    }

}