package com.yousef.el_mufassir.databse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private static MySharedPreference mySharedPreference;
    private final SharedPreferences sharedPreferences;

    private MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("EL-Mufassir", Activity.MODE_PRIVATE);
    }

    public static MySharedPreference newInstance(Context context){
        if(mySharedPreference==null){
            mySharedPreference=new MySharedPreference(context);
        }
        return mySharedPreference;
    }

    public void saveString(String key,String value) {
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public void saveInt(String key,int value) {
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor .putInt(key, value);
        prefsEditor.apply();
    }

    public String returnString(String key,String defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, defValue);
        }
        return defValue;
    }

    public int returnInt(String key,int defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, defValue);
        }
        return defValue;
    }
}
