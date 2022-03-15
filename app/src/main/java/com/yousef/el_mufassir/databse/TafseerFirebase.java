package com.yousef.el_mufassir.databse;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.functions.TafseerFunction;
import com.yousef.el_mufassir.model.Constants;
import com.yousef.el_mufassir.model.Tafseer;

import java.util.HashMap;
import java.util.Map;

public class TafseerFirebase {

    private final Constants constants;
    private final CollectionReference Users, BlockUsers,Opens;


    public TafseerFirebase() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        constants = Constants.newInstance();
        Users = firebaseFirestore.collection(constants.NEW_USERS);
        BlockUsers = firebaseFirestore.collection(constants.BLOCK_USER);
        Opens = firebaseFirestore.collection(constants.OPEN);
    }


    public void getOpenTafseer(Context context,MySharedPreference mySharedPreference, TafseerFunction tafseerFunction){
        Opens.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int SouraOpen = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int open = document.toObject(Integer.class);
                            SouraOpen++;
                            if(mySharedPreference.returnInt(constants.NUM_OF_OPEN_AYA,0)<open) {
                                mySharedPreference.saveInt(constants.NUM_OF_OPEN_AYA, open);
                                tafseerFunction.showNotification(context.getResources().getStringArray(R.array.Quran)[SouraOpen-1],SouraOpen+"",open+"");
                            }
                        }
                        mySharedPreference.saveInt(constants.NUM_OF_OPEN_SOURA, SouraOpen);
                    }
                });
    }

}
