package com.yousef.el_mufassir.activity;

import android.Manifest;
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
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.adapter.PagerAdapter;
import com.yousef.el_mufassir.databinding.ActivityHomeBinding;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.fragments.AzkarFragment;
import com.yousef.el_mufassir.fragments.HomeFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    private Repository repository;
    private ActivityResultLauncher<String> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AzkarFragment());
        PagerAdapter pagerAdapter=new PagerAdapter(this,fragments);
        binding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager, (tab, position) -> tab.setText(getResources().getStringArray(R.array.Fragments)[position])).attach();
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getStringArray(R.array.Fragments)[tab.getPosition()]);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        repository=new Repository(this);
        repository.setAlert();
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
            callMe.setOnClickListener(v -> activityResultLauncher.launch(Manifest.permission.CALL_PHONE) );
            bottomSheetDialogSoura.show();
        }
        return super.onOptionsItemSelected(item);
    }
}