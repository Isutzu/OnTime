package com.example.oscar.ontime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by oscar on 2/16/17.
 */

public class MyCountDownTimer extends CountDownTimer
{
    private TextView tvCount;
    Context context;
   static boolean sendNotification = false;





    public MyCountDownTimer(Context context, TextView tvCount, long millisInFuture, long countDownInterval)
    {
        super(millisInFuture,countDownInterval);
        this.tvCount = tvCount;
        this.context = context;
    }


    @Override
    public void onTick(long millisUntilFinished)
    {
        String remainingTime = ((millisUntilFinished / 60000)+"'" + ":"+(millisUntilFinished % 60000 / 1000) +"''" );
        tvCount.setText("" + ((millisUntilFinished / 60000) + "'" + ":" + (millisUntilFinished % 60000 / 1000) + "''"));

        if (sendNotification)
        {
            updateNotifications(remainingTime);
        }
        else
        {
            NotificationManager nm =  (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(1001);
        }


    }

    private void updateNotifications(String remainingTime)
    {
        Intent in = new Intent(context, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, in, 0);

        NotificationManager nm =  (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setContentTitle("Remaining time: ");
        notification.setContentText(remainingTime);
        notification.setColor(34567);
        notification.setContentIntent(pi);
        notification.setOngoing(true);
        notification.setSmallIcon(R.drawable.clock);
        notification.setTicker("Alarm on");
        nm.notify(1001,notification.build());
    }

    @Override
    public void onFinish()
    {
        tvCount.setText("done!");
//        Intent i = new Intent(context,StopAlarmActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

    }

    public static void setSendNotification(boolean sendNotification1)
    {
        sendNotification = sendNotification1;
    }


}
