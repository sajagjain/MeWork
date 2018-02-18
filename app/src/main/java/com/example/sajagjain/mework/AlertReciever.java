package com.example.sajagjain.mework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by sajag jain on 16-02-2018.
 */

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Getting Values From Previous Activity
        String name=intent.getStringExtra("name");
        String desc=intent.getStringExtra("desc");

        //Setting Value To Notification
        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification(name,desc);
        notificationHelper.getManager().notify(1,nb.build());

        //Sending Values To Next Activities
        Intent i = new Intent();
        i.putExtra("name",name).putExtra("desc",desc);
        i.setClassName("com.example.sajagjain.mework", "com.example.sajagjain.mework.AlarmAlert");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
