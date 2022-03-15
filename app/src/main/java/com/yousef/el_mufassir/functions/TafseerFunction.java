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
import android.util.Log;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.activity.MainActivity;
import com.yousef.el_mufassir.databse.MySharedPreference;
import com.yousef.el_mufassir.model.Constants;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TafseerFunction {

    private final Context context;
    private final Constants constants;

    public TafseerFunction(Context context){
        this.context=context;
        constants=Constants.newInstance();
    }

    public String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.d("MAC",ex.getMessage());
        }
        return "02:00:00:00:00:00";
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

    public String[] getAyat(){
        String[]Ayat = new String[114];
        for (int i=0;i<114;i++){
            Ayat[i]=context.getResources().getStringArray(R.array.Number2)[Integer.parseInt(context.getResources().getStringArray(R.array.Numbers)[i])];
        }
        return Ayat;
    }


    public void showNotification(String name,String soura,String aya, String status){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(constants.CHANNEL_ID, constants.CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(context.getString(R.string.appName2));
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(constants.NUMBER,1);
        intent.putExtra(constants.NAME,"name");
        intent.putExtra(constants.AYA,1);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, constants.CHANNEL_ID);
        mBuilder.setSmallIcon(R.mipmap.ic_logo_round);
        mBuilder.setContentTitle(context.getString(R.string.soura)+name);
        if(status.equals(constants.NEW))
            mBuilder.setContentText(context.getString(R.string.canRead)+" "+aya+" "+context.getString(R.string.fromSoura)+" "+name );
        else if(status.equals(constants.EDIT))
            mBuilder.setContentText(context.getString(R.string.editAya)+" "+aya+" "+context.getString(R.string.fromSoura)+" "+name );
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true );
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        String id=soura+aya;
        notificationManagerCompat.notify(Integer.parseInt(id), mBuilder.build());
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
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Intent alertIntent = new Intent(context, MyAlarm.class);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(context, 0, alertIntent, 0));
            mySharedPreference.saveInt(constants.CHECK_ALARM,1);
        }
    }
}
