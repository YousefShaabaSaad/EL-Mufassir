package com.yousef.el_mufassir.databse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private final SharedPreferences sharedPreferences;
    public MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("EL-Mufassir", Activity.MODE_PRIVATE);
    }

    public void saveInt(String key,int value) {
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor .putInt(key, value);
        prefsEditor.apply();
    }

    public int returnInt(String key,int defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, defValue);
        }
        return defValue;
    }
}
