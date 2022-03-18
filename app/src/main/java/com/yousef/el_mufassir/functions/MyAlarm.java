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
        String time=String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if( !repository.returnString(constants.DATE,"Yousef").equals(time) ){
            repository.getNewAya();
            repository.saveString(constants.DATE,time);
        }
    }
}
