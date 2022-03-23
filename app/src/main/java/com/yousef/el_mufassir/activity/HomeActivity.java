package com.yousef.el_mufassir.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
    private int tabPosition=0;
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AzkarFragment());
        PagerAdapter pagerAdapter=new PagerAdapter(this,fragments);
        binding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager, (tab, position) -> tab.setText(getResources().getStringArray(R.array.Fragments)[position])).attach();
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition=tab.getPosition();
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
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result)
                        repository.callPhone();
                    else
                        TastyToast.makeText(this, getString( R.string.noPermissionCall ), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );

        binding.floatingActionButton.setOnClickListener(v -> TastyToast.makeText(this,getString(R.string.palestine),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show());
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
        else if (item.getItemId()==R.id.about)
            repository.about(findViewById( R.id.containerBottom ),activityResultLauncher);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(tabPosition==0)
            super.onBackPressed();
        else if(tabPosition==1)
            binding.viewPager.setCurrentItem(0);
    }
}