package com.yousef.el_mufassir.databse;

import android.content.Context;
import android.view.View;

import com.yousef.el_mufassir.functions.TafseerFunction;
import com.yousef.el_mufassir.model.Tafseer;


public class Repository {

    private final Context context;
    private final TafseerFirebase tafseerFirebase;
    private final TafseerFunction tafseerFunction;
    private final MySharedPreference mySharedPreference;


    public Repository(Context context){
        this.context=context;
        tafseerFirebase=new TafseerFirebase();
        tafseerFunction=new TafseerFunction(context);
        mySharedPreference=MySharedPreference.newInstance(context);
    }


    //MySharedPreference
    public String returnString(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }
    public int returnInt(String key,int defValue) {
        return mySharedPreference.returnInt(key, defValue);
    }


    //TafseerFunction
    public void callPhone() {
       tafseerFunction.callPhone();
    }
    public void whatsapp(){
        tafseerFunction.whatsapp();
    }
    public String[] getName(){
        return tafseerFunction.getName();
    }
    public String[] getAyat(){
        return tafseerFunction.getAyat();
    }
    public void getImageTafseer(View view,String name) {
        tafseerFunction.getImageTafseer(view,name);
    }
    public void getTextTafseer(String text) {
        tafseerFunction.getTextTafseer(text);
    }
    public void setAlert(){
        tafseerFunction.setAlert(mySharedPreference);
    }

    //TafseerFirebase
    public void saveUser(){
        tafseerFirebase.saveUser( tafseerFunction.getMacAddress() );
    }
    public void blockUser(){
        tafseerFirebase.blockUser(tafseerFunction.getMacAddress(),mySharedPreference);
    }
    public void getOpenTafseer(){
        tafseerFirebase.getOpenTafseer(context,mySharedPreference);
    }
}
