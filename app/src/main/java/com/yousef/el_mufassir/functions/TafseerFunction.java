package com.yousef.el_mufassir.functions;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.activity.MainActivity;
import com.yousef.el_mufassir.activity.TafseerActivity;
import com.yousef.el_mufassir.databse.MySharedPreference;
import com.yousef.el_mufassir.model.Constants;
import com.yousef.el_mufassir.model.Tafseer;

import java.util.Calendar;

public class TafseerFunction {

    private final Context context;
    private final Constants constants;

    public TafseerFunction(Context context){
        this.context=context;
        constants=Constants.newInstance();
    }

    public void callPhone() {
        String phone="tel:01142747343";
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData( Uri.parse( phone ) );
        context.startActivity( intent );
    }

    public void whatsapp(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData( Uri.parse("https://chat.whatsapp.com/DmdY42YcRW49fG7huCQlHv"));
        context.startActivity(intent);
    }

    public String[] getName(){
        return context.getResources().getStringArray(R.array.Quran);
    }

    public String[] getAzkar(){
        return context.getResources().getStringArray(R.array.Azkar);
    }

    public int[] getCount(MySharedPreference mySharedPreference){
        int[] counts=new int[30];
        for(int i=0;i<30;i++){
            String key=constants.COUNT+""+i;
            counts[i]=mySharedPreference.returnInt(key,0);
        }
        return counts ;
    }


    public String[] getAyat(){
        String[]Ayat = new String[114];
        for (int i=0;i<114;i++){
            Ayat[i]=context.getResources().getStringArray(R.array.Number2)[Integer.parseInt(context.getResources().getStringArray(R.array.Numbers)[i])];
        }
        return Ayat;
    }

    public void getNewAya(MySharedPreference mySharedPreference){
        int numberOfSoura=mySharedPreference.returnInt(constants.NUM_OF_OPEN_SOURA,0);
        int numberOfAya=mySharedPreference.returnInt(constants.NUM_OF_OPEN_AYA,0);
        mySharedPreference.saveInt(constants.NUM_OF_OPEN_AYA,numberOfAya+1);
       showNotification(getName()[numberOfSoura], String.valueOf(numberOfAya),numberOfSoura,numberOfAya);
    }

    private void showNotification(String name,String aya,int souraNum, int ayaNum){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(constants.CHANNEL_ID, constants.CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(context.getString(R.string.appName2));
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(context, TafseerActivity.class);
        intent.putExtra(constants.NUMBER,souraNum);
        intent.putExtra(constants.AYA,ayaNum);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, constants.CHANNEL_ID);
        mBuilder.setSmallIcon(R.mipmap.ic_logo_round);
        mBuilder.setContentTitle(context.getString(R.string.soura)+name);
        mBuilder.setContentText(context.getString(R.string.canRead)+" "+aya+" "+context.getString(R.string.fromSoura)+" "+name );
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true );
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        } else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public void getImageTafseer(View view,String name) {
        Bitmap image = getBitmapFromView(view);
        MediaStore.Images.Media.insertImage( context.getContentResolver(),image , name, context.getString(R.string.appName) );
    }

    public void getTextTafseer(String text) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final android.content.ClipData clipData = android.content.ClipData.newPlainText(context.getString(R.string.appName), text);
        clipboardManager.setPrimaryClip(clipData);
    }

    public void setAlert(MySharedPreference mySharedPreference){
        if(mySharedPreference.returnInt(constants.CHECK_ALARM,0)==0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 23);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Intent alertIntent = new Intent(context, MyAlarm.class);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(context, 0, alertIntent, 0));
            mySharedPreference.saveInt(constants.CHECK_ALARM,1);
        }
    }

    public Tafseer getAyaAndTafseer(int soura, int aya){
        Tafseer tafseer=new Tafseer();
        switch (soura){
            case 0:
                tafseer.setAyaText( context.getResources().getStringArray(R.array.one)[aya]);
                tafseer.setTafseerText( context.getResources().getStringArray(R.array.oneTafseer)[aya]);
                break;
            case 1:
                tafseer.setAyaText( context.getResources().getStringArray(R.array.two)[aya] );
                tafseer.setTafseerText( context.getResources().getStringArray(R.array.twoTafseer)[aya] ) ;
                break;
        }
        return tafseer;
    }
}
