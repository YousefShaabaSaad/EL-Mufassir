package com.yousef.el_mufassir.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yousef.el_mufassir.databse.Repository;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository=new Repository(context);
        repository.getNewAya();
    }
}
