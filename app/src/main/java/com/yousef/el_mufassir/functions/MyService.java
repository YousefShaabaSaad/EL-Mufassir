package com.yousef.el_mufassir.functions;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.yousef.el_mufassir.databse.Repository;


public class MyService extends Service {

    private Repository repository;
    public MyService(){
    }
    @Override
    public void onCreate() {
        super.onCreate();
        repository=new Repository(getBaseContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        repository.getOpenTafseer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repository.getOpenTafseer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
