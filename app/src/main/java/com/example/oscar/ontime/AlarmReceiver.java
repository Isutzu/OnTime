package com.example.oscar.ontime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

/**
 * Created by oscar on 2/3/17.
 */

public class AlarmReceiver extends BroadcastReceiver
{

    static Ringtone ringtone;
    static NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent)
    {

        String actionName = intent.getAction();

        if(actionName != null &&
                actionName.equals("com.example.oscar.ontime"))
        {

            sendNotification(context);

            Toast.makeText(context,"Time to go back ", Toast.LENGTH_SHORT).show();
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
        }





    }

    /**************** sendNotification() *************/
    public void sendNotification(Context context)
    {
        Uri uri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent in = new Intent(context,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi =PendingIntent
                .getActivity(context,0, in, 0);

        Intent stopMusicIntent = new Intent();
        stopMusicIntent.setAction("com.example.action.STOP_MUSIC");
        PendingIntent pi1 = PendingIntent.getBroadcast(context,0,stopMusicIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder  notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarm is on")
                .setContentText("tap the button to stop Alarm")
                .setSound(uri)
                .setContentIntent(pi)
                .setVisibility(VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher,"STOP ALARM",pi1);


         notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());



    }
    /**************** getRingtone() *****************/
    public static Ringtone getRingtone()
    {
        return ringtone;
    }

    public static NotificationManager getNotification()
    {
        return notificationManager;
    }

}
