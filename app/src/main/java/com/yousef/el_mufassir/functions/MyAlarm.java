package com.yousef.el_mufassir.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository=new Repository(context);
        repository.getNewAya();
        Constants constants=Constants.newInstance();
        repository.saveInt(constants.CHECK_ALARM,0);
    }
}
