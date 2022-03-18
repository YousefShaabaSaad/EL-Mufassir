package com.yousef.el_mufassir.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.model.Constants;

import java.util.Calendar;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository=new Repository(context);
        Constants constants=Constants.newInstance();
        int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if( repository.returnInt(constants.DAY,-1) != day ){
            repository.getNewAya();
            repository.saveInt(constants.DAY,day);
        }
    }
}
